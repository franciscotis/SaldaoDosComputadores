package model;

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

public class Servidor { // Servidor da loja principal - > Saldao Dos Computadores
    private static Loja loja = null; //Atributo do tipo Loja


    public static void main(String[] args) throws IOException, AlreadyBoundException, NotBoundException, ClassNotFoundException {
        Registry registry = LocateRegistry  .createRegistry(5099); //Registra no servidor RMI na porta t5099
        registry.bind("loja",getInstance()); //Exporta o objeto loja para o servidor RMI
        Scanner tc = new Scanner(System.in); //Teclado
            if(tc.nextLine().equals("1")){ //Caso receba um input 1
                System.exit(0); //Sai do programa
            }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> //Caso saia do proggrama
        {
            try {
                serializar(loja); //Chama o método serializar
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));


    }

    public static Loja getInstance() throws IOException, NotBoundException, ClassNotFoundException { //Método que retorna instância da loja
        if(loja==null){ //Caso loja seja igual a null
            try{
            FileInputStream fileIn = new FileInputStream("loja.ser"); //Tenta deserializar o objeto
            ObjectInputStream in = new ObjectInputStream(fileIn);
            loja = (Loja) in.readObject(); //Atribui loja ao objeto desserializado
            in.close();
            fileIn.close();
                System.out.println("Objeto Desserializado com sucesso!");}
            catch(Exception e){ //Caso contrário
                System.out.println("Novo objeto criado!");
                loja = new Loja(); //instancia uma nova loja
            }
            finally{
                System.out.println("SEVIDOR INICIADO!");
            }
        }
        return loja;
    }
    public static void serializar(Loja e) throws IOException { //Método que serializa o objeto
        FileOutputStream fileOut = new FileOutputStream("loja.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(e); //Serializa o objeto
        out.close();
        fileOut.close();
    }




}
