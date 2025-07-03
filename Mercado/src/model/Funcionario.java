package model;

public class Funcionario {
	
	private String idFuncionario;
	private String nomeFuncionario;
	private String cpfFuncionario;
	private String dataNasc;
	private String telefone;
	private String endereco;
	private String email;
	private String cargo;
	private String genero;
	private String salario;


	private String nivel;
	
	public Funcionario(String idFuncionario, String nomeFuncionario, String cpfFuncionario, String dataNasc,
			String telefone, String endereco, String email, String genero, String cargo, String nivel, String salario) {
		super();
		this.idFuncionario = idFuncionario;
		this.nomeFuncionario = nomeFuncionario;
		this.cpfFuncionario = cpfFuncionario;
		this.dataNasc = dataNasc;
		this.telefone = telefone;
		this.endereco = endereco;
		this.genero = genero;
		this.email = email;
		this.cargo = cargo;
		this.nivel = nivel;
		this.salario = salario;
	}

	public Funcionario() {
		super();
	}

	public String getIdFuncionario() {
		return idFuncionario;
	}

	public void funcionario(String idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public String getNomeFuncionario() {
		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}

	public String getCpfFuncionario() {
		return cpfFuncionario;
	}

	public void setCpfFuncionario(String cpfFuncionario) {
		this.cpfFuncionario = cpfFuncionario;
	}

	public String getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(String dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getSalario() {
		return salario;
	}

	public void setSalario(String salario) {
		this.salario = salario;
	}


	
	
	
	
	
}