package model;

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Servidor { // Servidor da loja principal - > Saldao Dos Computadores
    private static Loja loja = null;
    public static void main(String[] args) throws IOException, AlreadyBoundException, NotBoundException, ClassNotFoundException {
        Scanner teclado = new Scanner(System.in);
        Registry registry = LocateRegistry.createRegistry(5099);
        registry.bind("loja",getInstance());
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
            try {
                serializar(loja);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        if(teclado.nextLine().equals("1")){
            System.exit(0);
        }
    }

    public static Loja getInstance() throws IOException, NotBoundException, ClassNotFoundException {
        if(loja==null){
            try{
            FileInputStream fileIn = new FileInputStream("loja.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            loja = (Loja) in.readObject();
            in.close();
            fileIn.close();
                System.out.println("ok");}
            catch(Exception e){
                System.out.println("entrou aqui");
                loja = new Loja();
            }
        }
        return loja;
    }
    public static void serializar(Loja e) throws IOException {
        FileOutputStream fileOut = new FileOutputStream("loja.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(e);
        out.close();
        fileOut.close();
    }



}
