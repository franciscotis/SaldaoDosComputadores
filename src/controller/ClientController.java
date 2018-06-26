package controller;
import java.io.Serializable;
import model.Client;
import model.Loja;
import model.LojaInterface;
import model.Produto;

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class ClientController implements  Serializable{ // CLIENT
    private static final long serialVersionUID = 1L;

    private static String currentStore = "";



    private static ClientController INSTANCE =  null;
    // instancias de todos os outros clientes
    private List<Client> lojas;
    private static String ip = "";
    private static String ips = "";


    public ClientController() throws AlreadyBoundException, IOException, NotBoundException {
        this.readIP();
        this.readIPServer();
        this.lojas = new ArrayList<Client>();
        this.lojas.add(new Client("Mexicanas")); //Mexicanas
        this.lojas.add(new Client("Amazonas")); // Amazonas
        this.lojas.add(new Client("Mazza")); // Mazza

        LojaInterface loja = (LojaInterface) Naming.lookup("rmi://"+ClientController.getIps()+":5099/loja");
        loja.Add(this.lojas.get(0));
        loja.Add(this.lojas.get(1));
        loja.Add(this.lojas.get(2));
        /*
        Registry registry = LocateRegistry.createRegistry(5098);
        registry.bind("Mexicanas",this.lojas.get(0));//Mexicanas
        registry.bind("Amazonas",this.lojas.get(1)); //Amazonas
        registry.bind("Mazza",this.lojas.get(2)); //Mazza

*/
        readFile("Mexicanas.csv",this.lojas.get(0));
        readFile("Amazonas.csv",this.lojas.get(1));
        readFile("Mazza.csv",this.lojas.get(2));

        this.leituraAfiliada("Mexicanas");
        this.leituraAfiliada("Amazonas");
        this.leituraAfiliada("Mazza");
    }





    private void leituraAfiliada(String nome) throws RemoteException, NotBoundException, MalformedURLException {
        LojaInterface loja = (LojaInterface) Naming.lookup("rmi://"+ips+":5099/loja");
        loja.getAfiliadas().forEach((k, v) -> {
            if(k.equals(nome)){
                for(Produto p : v){
                    for(Client client : this.lojas){
                        if(client.getNome().equals(nome)){
                            client.editarProduto(p.getNome(),p.getQuantidade());
                        }
                    }
                }
            }

        });

    }


    public void writeFile(String nome) throws IOException{
        BufferedWriter bw = null;
        FileWriter fw = null;
        System.out.println("write file");

        try {

            fw = new FileWriter(nome+".csv");
            bw = new BufferedWriter(fw);
            for(Client cl : this.lojas) {
                if (cl.getNome().equals(nome)) {
                    System.out.println(cl.getNome());
                    for (Produto prod : cl.getProdutos()) {
                        System.out.println(prod.getNome() + "<<");
                        bw.write(prod.getNome() + ";" + prod.getQuantidade());
                        bw.write("\n");
                    }
                }
            }


            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

    }

    private void readFile(String nomeArq, Client loja) throws IOException {
        InputStream is = getClass().getResourceAsStream(nomeArq);
        InputStreamReader isr =  new InputStreamReader(is);
        BufferedReader  br = new BufferedReader(isr);
        String line;
        while((line = br.readLine())!= null){
            String[] dadosUsuario = line.split(";");
            loja.addProduto(dadosUsuario[0],Integer.parseInt(dadosUsuario[1]));
        }
        br.close();
        isr.close();
        is.close();


    }

    public void sair() throws RemoteException, MalformedURLException, NotBoundException {
        LojaInterface loja = (LojaInterface) Naming.lookup("rmi://localhost:5099/loja");

        loja.Remove(this.lojas.get(0));
        loja.Remove(this.lojas.get(1));
        loja.Remove(this.lojas.get(2));
    }

    private void readIP() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("ipconfig.txt")));
        String linha = "";
        while((linha = reader.readLine())!=null){
            this.ip=linha.trim();
            break;
        }
        reader.close();

    }

    private void readIPServer() throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("ipconfigserver.txt")));
        String linha = "";
        while((linha = reader.readLine())!=null){
            ips=linha.trim();
            break;
        }
        reader.close();
    }

    public static ClientController getInstance() throws AlreadyBoundException, IOException, NotBoundException {
        if(INSTANCE==null){
            INSTANCE = new ClientController();
        }
        return INSTANCE;
    }

    public  String getCurrentStore() {
        return currentStore;
    }

    public  void setCurrentStore(String currentStore) {
        ClientController.currentStore = currentStore;
    }

    public List<Client> getLojas() {
        return lojas;
    }
    public static String getIp() {
        return ip;
    }

    public static String getIps() {
        return ips;
    }
}
