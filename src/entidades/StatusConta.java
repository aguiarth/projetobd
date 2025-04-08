package entidades;

public enum StatusConta {
    PAGO("Pago"),
    PENDENTE("Pendente"),
    VENCIDO("Vencido");

    private final String descricao;

    StatusConta(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
