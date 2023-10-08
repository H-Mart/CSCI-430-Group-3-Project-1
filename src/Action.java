import java.io.Serializable;

interface Action extends Serializable {
    long serialVersionUID = 1L;

    void execute();
}