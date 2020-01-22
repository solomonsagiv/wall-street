package tables.status;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalTime;

@MappedSuperclass
public abstract class TablesArraysFather {

    @Id
    @Column( name = "id" )
    private int id;
    @Column( name = "time" )
    private String time;
    @Column( name = "name" )
    private String name;

    public TablesArraysFather( int id, String name ) {
        this.id = id;
        this.time = LocalTime.now( ).toString( );
        this.name = name;

    }

    public TablesArraysFather() {
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime( String time ) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }
}
