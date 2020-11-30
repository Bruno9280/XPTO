/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author bruno
 */
public class Consultas {

    private String uf;
    private int qnt;

    public Consultas() {
    }

    public Consultas(String uf, int qnt) {
        this.uf = uf;
        this.qnt = qnt;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public int getQnt() {
        return qnt;
    }

    public void setQnt(int qnt) {
        this.qnt = qnt;
    }

    @Override
    public String toString() {
        return "Consultas{" + "uf=" + uf + ", qnt=" + qnt + '}';
    }

}
