package com.usuario.quero_ler.fixtures;

import com.usuario.quero_ler.core.entities.*;
import com.usuario.quero_ler.core.enums.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public final class EntityBuilders {

    private EntityBuilders() {}

    public static UserBuilder user() { return new UserBuilder(); }
    public static UsuarioBuilder usuario() { return new UsuarioBuilder(); }
    public static LivroBuilder livro() { return new LivroBuilder(); }
    public static LeituraBuilder leitura() { return new LeituraBuilder(); }
    public static DiarioDeLeituraBuilder diarioDeLeitura() { return new DiarioDeLeituraBuilder(); }
    public static MetaLeituraBuilder metaLeitura() { return new MetaLeituraBuilder(); }
    public static NotificacaoBuilder notificacao() { return new NotificacaoBuilder(); }
    public static UsuarioNotificacaoBuilder usuarioNotificacao() { return new UsuarioNotificacaoBuilder(); }
    public static AutorBuilder autor() { return new AutorBuilder(); }
    public static DocumentoBuilder documento() { return new DocumentoBuilder(); }
    public static AcompanhamentoDeLeituraBuilder acompanhamentoDeLeitura() { return new AcompanhamentoDeLeituraBuilder(); }
    public static LivroMetaBuilder livroMeta() { return new LivroMetaBuilder(); }

    public static class UserBuilder {
        private Long id;
        private String user;
        private String senha;
        private Boolean senhaTrocada;
        private UsuarioProfile profile;
        private Usuario usuario;

        public UserBuilder id(Long id) { this.id = id; return this; }
        public UserBuilder user(String user) { this.user = user; return this; }
        public UserBuilder senha(String senha) { this.senha = senha; return this; }
        public UserBuilder senhaTrocada(Boolean v) { this.senhaTrocada = v; return this; }
        public UserBuilder senhaTrocada(boolean v) { this.senhaTrocada = v; return this; }
        public UserBuilder profile(UsuarioProfile profile) { this.profile = profile; return this; }
        public UserBuilder usuario(Usuario usuario) { this.usuario = usuario; return this; }
        public User build() { return new User(id, user, senha, senhaTrocada, profile, usuario); }
    }

    public static class UsuarioBuilder {
        private Long id;
        private String nome;
        private String email;
        private String cpf;
        private LocalDate dataDeNascimento;
        private Boolean aceitarTermos;
        private String cidade;
        private String estado;
        private String pais;
        private byte[] foto;
        private User user;
        private List<UsuarioNotificacao> notificacoes;
        private List<Leitura> livros;
        private List<MetaLeitura> metasLeitura;

        public UsuarioBuilder id(Long id) { this.id = id; return this; }
        public UsuarioBuilder nome(String v) { this.nome = v; return this; }
        public UsuarioBuilder email(String v) { this.email = v; return this; }
        public UsuarioBuilder cpf(String v) { this.cpf = v; return this; }
        public UsuarioBuilder dataDeNascimento(LocalDate v) { this.dataDeNascimento = v; return this; }
        public UsuarioBuilder aceitarTermos(Boolean v) { this.aceitarTermos = v; return this; }
        public UsuarioBuilder cidade(String v) { this.cidade = v; return this; }
        public UsuarioBuilder estado(String v) { this.estado = v; return this; }
        public UsuarioBuilder pais(String v) { this.pais = v; return this; }
        public UsuarioBuilder foto(byte[] v) { this.foto = v; return this; }
        public UsuarioBuilder user(User v) { this.user = v; return this; }
        public UsuarioBuilder notificacoes(List<UsuarioNotificacao> v) { this.notificacoes = v; return this; }
        public UsuarioBuilder livros(List<Leitura> v) { this.livros = v; return this; }
        public UsuarioBuilder metasLeitura(List<MetaLeitura> v) { this.metasLeitura = v; return this; }
        public Usuario build() {
            return new Usuario(id, nome, email, cpf, dataDeNascimento, aceitarTermos,
                    cidade, estado, pais, foto, user, notificacoes, livros, metasLeitura);
        }
    }

    public static class LivroBuilder {
        private Long id;
        private String titulo;
        private String isbn;
        private String editora;
        private java.time.Year anoDePublicacao;
        private Integer numeroDePaginas;
        private LivroIdioma idioma;
        private String sinopse;
        private byte[] capaDoLivro;
        private List<Autor> autores;
        private List<Leitura> usuarios;
        private java.time.LocalDateTime dataDeCadastro;
        private Integer quantidadeDeUso;

        public LivroBuilder id(Long id) { this.id = id; return this; }
        public LivroBuilder titulo(String v) { this.titulo = v; return this; }
        public LivroBuilder isbn(String v) { this.isbn = v; return this; }
        public LivroBuilder editora(String v) { this.editora = v; return this; }
        public LivroBuilder anoDePublicacao(java.time.Year v) { this.anoDePublicacao = v; return this; }
        public LivroBuilder numeroDePaginas(Integer v) { this.numeroDePaginas = v; return this; }
        public LivroBuilder idioma(LivroIdioma v) { this.idioma = v; return this; }
        public LivroBuilder sinopse(String v) { this.sinopse = v; return this; }
        public LivroBuilder capaDoLivro(byte[] v) { this.capaDoLivro = v; return this; }
        public LivroBuilder autores(List<Autor> v) { this.autores = v; return this; }
        public LivroBuilder usuarios(List<Leitura> v) { this.usuarios = v; return this; }
        public LivroBuilder dataDeCadastro(LocalDateTime v) { this.dataDeCadastro = v; return this; }
        public LivroBuilder quantidadeDeUso(Integer v) { this.quantidadeDeUso = v; return this; }
        public Livro build() {
            return new Livro(id, titulo, isbn, editora, anoDePublicacao, numeroDePaginas,
                    idioma, sinopse, capaDoLivro, autores, usuarios, dataDeCadastro, quantidadeDeUso);
        }
    }

    public static class LeituraBuilder {
        private Long id;
        private LeituraStatus status;
        private Usuario usuario;
        private Livro livro;
        private DiarioDeLeitura diarioDeLeitura;
        private Boolean lido;

        public LeituraBuilder id(Long id) { this.id = id; return this; }
        public LeituraBuilder status(LeituraStatus v) { this.status = v; return this; }
        public LeituraBuilder usuario(Usuario v) { this.usuario = v; return this; }
        public LeituraBuilder livro(Livro v) { this.livro = v; return this; }
        public LeituraBuilder diarioDeLeitura(DiarioDeLeitura v) { this.diarioDeLeitura = v; return this; }
        public LeituraBuilder lido(Boolean v) { this.lido = v; return this; }
        public Leitura build() {
            return new Leitura(id, status, usuario, livro, diarioDeLeitura, lido);
        }
    }

    public static class DiarioDeLeituraBuilder {
        private Long id;
        private Leitura leitura;
        private LocalDateTime inicioDaLeitura;
        private LocalDateTime terminoDaLeitura;
        private Integer paginasLidas;
        private List<AcompanhamentoDeLeitura> comentarios;
        private Double nota;
        private String tituloDaResenha;
        private Boolean spoiler;
        private String resenha;

        public DiarioDeLeituraBuilder id(Long id) { this.id = id; return this; }
        public DiarioDeLeituraBuilder leitura(Leitura v) { this.leitura = v; return this; }
        public DiarioDeLeituraBuilder inicioDaLeitura(LocalDateTime v) { this.inicioDaLeitura = v; return this; }
        public DiarioDeLeituraBuilder terminoDaLeitura(LocalDateTime v) { this.terminoDaLeitura = v; return this; }
        public DiarioDeLeituraBuilder paginasLidas(Integer v) { this.paginasLidas = v; return this; }
        public DiarioDeLeituraBuilder comentarios(List<AcompanhamentoDeLeitura> v) { this.comentarios = v; return this; }
        public DiarioDeLeituraBuilder nota(Double v) { this.nota = v; return this; }
        public DiarioDeLeituraBuilder tituloDaResenha(String v) { this.tituloDaResenha = v; return this; }
        public DiarioDeLeituraBuilder spoiler(Boolean v) { this.spoiler = v; return this; }
        public DiarioDeLeituraBuilder resenha(String v) { this.resenha = v; return this; }
        public DiarioDeLeitura build() {
            return new DiarioDeLeitura(id, leitura, inicioDaLeitura, terminoDaLeitura,
                    paginasLidas, comentarios, nota, tituloDaResenha, spoiler, resenha);
        }
    }

    public static class MetaLeituraBuilder {
        private Long id;
        private Integer ano;
        private Integer metaLivrosAno;
        private Integer metaLivrosMes;
        private Integer metaPaginasDia;
        private Usuario usuario;
        private List<LivroMeta> livrosMeta;

        public MetaLeituraBuilder id(Long id) { this.id = id; return this; }
        public MetaLeituraBuilder ano(Integer v) { this.ano = v; return this; }
        public MetaLeituraBuilder metaLivrosAno(Integer v) { this.metaLivrosAno = v; return this; }
        public MetaLeituraBuilder metaLivrosMes(Integer v) { this.metaLivrosMes = v; return this; }
        public MetaLeituraBuilder metaPaginasDia(Integer v) { this.metaPaginasDia = v; return this; }
        public MetaLeituraBuilder usuario(Usuario v) { this.usuario = v; return this; }
        public MetaLeituraBuilder livrosMeta(List<LivroMeta> v) { this.livrosMeta = v; return this; }
        public MetaLeitura build() {
            return new MetaLeitura(id, ano, metaLivrosAno, metaLivrosMes, metaPaginasDia, usuario, livrosMeta);
        }
    }

    public static class NotificacaoBuilder {
        private Long id;
        private LocalDateTime dataDeCriacao;
        private String notificacao;
        private Boolean lido;

        public NotificacaoBuilder id(Long id) { this.id = id; return this; }
        public NotificacaoBuilder dataDeCriacao(LocalDateTime v) { this.dataDeCriacao = v; return this; }
        public NotificacaoBuilder notificacao(String v) { this.notificacao = v; return this; }
        public NotificacaoBuilder lido(Boolean v) { this.lido = v; return this; }
        public Notificacao build() {
            return new Notificacao(id, dataDeCriacao, notificacao, lido);
        }
    }

    public static class UsuarioNotificacaoBuilder {
        private Long id;
        private Usuario usuario;
        private Notificacao notificacao;
        private Boolean visualizada;
        private LocalDateTime dataLeitura;

        public UsuarioNotificacaoBuilder id(Long id) { this.id = id; return this; }
        public UsuarioNotificacaoBuilder usuario(Usuario v) { this.usuario = v; return this; }
        public UsuarioNotificacaoBuilder notificacao(Notificacao v) { this.notificacao = v; return this; }
        public UsuarioNotificacaoBuilder visualizada(Boolean v) { this.visualizada = v; return this; }
        public UsuarioNotificacaoBuilder dataLeitura(LocalDateTime v) { this.dataLeitura = v; return this; }
        public UsuarioNotificacao build() {
            return new UsuarioNotificacao(id, usuario, notificacao, visualizada, dataLeitura);
        }
    }

    public static class AutorBuilder {
        private Long id;
        private String nome;
        private List<Livro> livros;

        public AutorBuilder id(Long id) { this.id = id; return this; }
        public AutorBuilder nome(String v) { this.nome = v; return this; }
        public AutorBuilder livros(List<Livro> v) { this.livros = v; return this; }
        public Autor build() { return new Autor(id, nome, livros); }
    }

    public static class DocumentoBuilder {
        private Long id;
        private String titulo;
        private DocumentoTipo tipo;
        private String conteudo;
        private LocalDateTime ultimaAlteracao;

        public DocumentoBuilder id(Long id) { this.id = id; return this; }
        public DocumentoBuilder titulo(String v) { this.titulo = v; return this; }
        public DocumentoBuilder tipo(DocumentoTipo v) { this.tipo = v; return this; }
        public DocumentoBuilder conteudo(String v) { this.conteudo = v; return this; }
        public DocumentoBuilder ultimaAlteracao(LocalDateTime v) { this.ultimaAlteracao = v; return this; }
        public Documento build() {
            return new Documento(id, titulo, tipo, conteudo, ultimaAlteracao);
        }
    }

    public static class AcompanhamentoDeLeituraBuilder {
        private Long id;
        private int paginaInicial;
        private int paginaFinal;
        private String comentario;
        private DiarioDeLeitura diarioDeLeitura;

        public AcompanhamentoDeLeituraBuilder id(Long id) { this.id = id; return this; }
        public AcompanhamentoDeLeituraBuilder paginaInicial(int v) { this.paginaInicial = v; return this; }
        public AcompanhamentoDeLeituraBuilder paginaFinal(int v) { this.paginaFinal = v; return this; }
        public AcompanhamentoDeLeituraBuilder comentario(String v) { this.comentario = v; return this; }
        public AcompanhamentoDeLeituraBuilder diarioDeLeitura(DiarioDeLeitura v) { this.diarioDeLeitura = v; return this; }
        public AcompanhamentoDeLeitura build() {
            return new AcompanhamentoDeLeitura(id, paginaInicial, paginaFinal, comentario, diarioDeLeitura);
        }
    }

    public static class LivroMetaBuilder {
        private Long id;
        private MetaLeitura metaLeitura;
        private Livro livro;

        public LivroMetaBuilder id(Long id) { this.id = id; return this; }
        public LivroMetaBuilder metaLeitura(MetaLeitura v) { this.metaLeitura = v; return this; }
        public LivroMetaBuilder livro(Livro v) { this.livro = v; return this; }
        public LivroMeta build() { return new LivroMeta(id, metaLeitura, livro); }
    }
}
