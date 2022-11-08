package Ex14;

import java.io.Serializable;

public class Data implements Serializable {
    int nr_workers;
    int n_intervalos;
    int id;

    public Data (int nr_workers, int n_intervalos, int id){
        this.n_intervalos = n_intervalos;
        this.nr_workers = nr_workers;
        this.id = id;
    }

    public void setIndex(int n){
        this.id = n;
    }
}
