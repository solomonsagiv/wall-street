package tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "setting" )
public class SettingTable {

    @Id
    @Column( name = "id" )
    private int id;
    @Column( name = "dax_exp_dir" )
    private String dax_exp_dir;
    @Column( name = "ndx_exp_dir" )
    private String ndx_exp_dir;

    // Constructor
    public SettingTable( String dax_exp_dir, String ndx_exp_dir ) {
        this.dax_exp_dir = dax_exp_dir;
        this.ndx_exp_dir = ndx_exp_dir;
    }

    // Empty constructor
    public SettingTable() {
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getDax_exp_dir() {
        return dax_exp_dir;
    }

    public void setDax_exp_dir( String dax_exp_dir ) {
        this.dax_exp_dir = dax_exp_dir;
    }

    public String getNdx_exp_dir() {
        return ndx_exp_dir;
    }

    public void setNdx_exp_dir( String ndx_exp_dir ) {
        this.ndx_exp_dir = ndx_exp_dir;
    }

    @Override
    public String toString() {
        return "SerttingTable [id=" + id + ", dax_exp_dir=" + dax_exp_dir + ", ndx_exp_dir=" + ndx_exp_dir + "]";
    }


}
