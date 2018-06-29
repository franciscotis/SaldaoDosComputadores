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

public class ClientController implements  Serializable{ // Controller do cliente
    private static final long serialVersionUID = 1L; //Versão da serialização

    private static String currentStore = ""; //CurrentStore vai guardar qual a loja que o usuário está atualmente visitando

    private static ClientController INSTANCE =  null; // Instância do Client
    private List<Client> lojas; //Lista de todas as lojas
    private static String ips = ""; // Variável que irá guardar o ip do servidor


    public ClientController() throws AlreadyBoundException, IOException, NotBoundException {
        this.readIPServer(); //Chama o método que lê o servidor.
        this.lojas = new ArrayList<Client>(); //Declaração  do array de lojas
        this.lojas.add(new Client("Mexicanas")); //Adiciono a loja Mexicanas no array
        this.lojas.add(new Client("Amazonas")); // Adiciono a loja Amazonas no array
        this.lojas.add(new Client("Mazza")); // Adiciono a loja Mazza no array

        LojaInterface loja = (LojaInterface) Naming.lookup("rmi://"+ClientController.getIps()+":5099/loja"); //Método RMI que irá buscar no servidor RMI o objeto

        loja.Add(this.lojas.get(0)); //Adiciono no servidor uma filial
        loja.Add(this.lojas.get(1)); //Adiciono no servidor uma filial
        loja.Add(this.lojas.get(2)); //Adiciono no servidor uma filial
        readFile("Mexicanas.csv",this.lojas.get(0)); //Leio os arquivos contendo a quantidade de estoque dos produtos
        readFile("Amazonas.csv",this.lojas.get(1));
        readFile("Mazza.csv",this.lojas.get(2));

        // Como o estoque local pode estar desatualizado, o sistema consulta ao servidor central que contém o estoque central
        // de todas as lojas. E o estoque de todas é atualizado
        this.leituraAfiliada("Mexicanas");
        this.leituraAfiliada("Amazonas");
        this.leituraAfiliada("Mazza");
    }





    private void leituraAfiliada(String nome) throws RemoteException, NotBoundException, MalformedURLException {
        //Método que verifica o estoque  das lojas conforme armazenamento no estoque central
        LojaInterface loja = (LojaInterface) Naming.lookup("rmi://"+ips+":5099/loja"); //Objeto remoto
        loja.getAfiliadas().forEach((k, v) -> { //Percorre o hash
            if(k.equals(nome)){ //Caso a loja for a que esteja passando por parâmetro
                for(Produto p : v){ //Percorre os produtos
                    for(Client client : this.lojas){ //Percorre as lojas
                        if(client.getNome().equals(nome)){ //Caso o nome da loja seja igual a que esteja passando por parametro
                            client.editarProduto(p.getNome(),p.getQuantidade()); //Edita a quantidade do produto
                        }
                    }
                }
            }

        });

    }


    public void writeFile(String nome) throws IOException{ //Método que salva o arquivo .csv as informações dos produtos
        BufferedWriter bw = null;
        FileWriter fw = null;
        System.out.println("Salvando os dados no arquivo");

        try {

            fw = new FileWriter(nome+".csv");
            bw = new BufferedWriter(fw);
            for(Client cl : this.lojas) { //Para cada produto ele irá salvar uma linha
                if (cl.getNome().equals(nome)) {
                    for (Produto prod : cl.getProdutos()) {
                        bw.write(prod.getNome() + ";" + prod.getQuantidade()); //Salva no formato .csv
                        bw.write("\n"); //Adiciona uma quebra de linha
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

    private void readFile(String nomeArq, Client loja) throws IOException { //Método que lê o arquivo .csv e armazena os dados dos produtos
        InputStream is = getClass().getResourceAsStream(nomeArq);
        InputStreamReader isr =  new InputStreamReader(is);
        BufferedReader  br = new BufferedReader(isr);
        String line;
        while((line = br.readLine())!= null){
            String[] dadosUsuario = line.split(";");  //Separa a linha pelo ;
            loja.addProduto(dadosUsuario[0],Integer.parseInt(dadosUsuario[1])); //Armazena a quantidade dos produtos
        }
        br.close();
        isr.close();
        is.close();

    }

    public void sair() throws RemoteException, MalformedURLException, NotBoundException {
        //Método que retira a loja da lista de lojas ativas do servidor
        LojaInterface loja = (LojaInterface) Naming.lookup("rmi://"+ClientController.getIps()+":5099/loja"); //Objeto remoto
        loja.Remove(this.lojas.get(0)); // Remove
        loja.Remove(this.lojas.get(1));
        loja.Remove(this.lojas.get(2));
    }

    private void readIPServer() throws IOException{ //Método que lê o o .txt contendo o endereço ip do servidor
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("ipconfigserver.txt")));
        String linha = "";
        while((linha = reader.readLine())!=null){
            ips=linha.trim();
            break;
        }
        reader.close();
    }

    public static ClientController getInstance() throws AlreadyBoundException, IOException, NotBoundException { //Método que retorna uma instância de ClientController
        if(INSTANCE==null){
            INSTANCE = new ClientController();
        }
        return INSTANCE;
    }

    public  String getCurrentStore() {
        return currentStore;
    } //Método que retorna a loja que o cliente está visitando no momento

    public  void setCurrentStore(String currentStore) {
        ClientController.currentStore = currentStore;
    } // Método que modifica a loja que o cliente está visitando no momento

    public List<Client> getLojas() {
        return lojas;
    } //Método que retorna as lojas

    public static String getIps() {
        return ips;
    } //Método que retonar o endereço ip do servidor
}
