package cadastrobd.enums;

public enum TipoPessoa {
    FISICA(1),
    JURIDICA(2);

    private final int tipo;

    TipoPessoa(int tipo) {
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }
}
