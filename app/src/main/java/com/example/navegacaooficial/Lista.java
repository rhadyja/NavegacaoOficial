package com.example.navegacaooficial;

public class Lista <T> {

    private Node<T> inicio;
    private int quantidade;

    public void inserir(T novo) {
        Node<T> novoNo = new Node<T>(novo);
        if(inicio == null) {
            inicio = novoNo;
        }
        else {
            Node<T> aux = inicio;
            while(aux.proximo != null) {
                aux = aux.proximo;
            }
            aux.proximo= novoNo;
        }
        quantidade++;
    }

    public String toString(){
        String s = "";
        Node<T> aux = inicio;
        while(aux != null) {
            s = s + aux + ",";
            aux = aux.proximo;
        }
        return "[" + s + "]";
    }

    public T consultar(int index) {
        if(index < quantidade) {
            int i = 0;
            Node<T> aux = inicio;
            while(i != index) {
                aux = aux.proximo;
                i++;
            }
            return aux.informacao;
        }
        else {
            return null;
        }
    }

    public int size() {
        return quantidade;
    }

    public void print() {
        System.out.println(this);
    }
}
