package com.Anelie.vitrineVirtual.models;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 150)
    private String nome;
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
    @Column(columnDefinition = "TEXT")
    private String descricao;
    @Column(length = 100)
    private String material;
    @Column(nullable = false)
    private String imagem;
    @Column(nullable = false)
    private BigDecimal valor;
    @Column(name = "data_cadastro", updatable = false)
    private LocalDateTime dataCadastro;
    private boolean destaque;
    private boolean oferta;


    @PrePersist
    protected void inCreate() {
        this.dataCadastro = LocalDateTime.now();
    }

    //gets e sets
    public Long getId(){ return id;}
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
    public String getImagem() { return imagem; }
    public void setImagem(String imagem) { this.imagem = imagem; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }
    public boolean isDestaque() {
        return destaque;
    }
    public void setDestaque(boolean destaque) {
        this.destaque = destaque;
    }

    public boolean isOferta() {
        return oferta;
    }
    public void setOferta(boolean oferta) {
        this.oferta = oferta;
    }

}
