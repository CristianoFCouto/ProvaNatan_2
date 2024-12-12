package contas;


public abstract class Conta {

    private String titular;
    private double saldo;
    private double limite;
    private double chequeEspecial;
    
	public Conta(String titular, double saldo, double limite, double especial) {
        this.titular = titular;
        this.saldo = saldo;
        this.limite = limite;
        this.chequeEspecial = especial;
	}
	
	public Conta(String titular, double saldo, double limite) {
		this.titular = titular;
        this.saldo = saldo;
        this.limite = limite;
	}

	public Conta(String titular, double saldo) {
		 this.titular = titular;
        this.saldo = saldo;
	}

	
	public String getTitular() {
        return this.titular;
    }

	public double getLimite() {
        return this.limite;
    }
    
	public double getSaldo() {
        return this.saldo;
	}
    
	public double getChequeEspecial() {
        return chequeEspecial;
    }

	public void setLimite(double limite) {
    	this.limite = limite;
    }
    
	public void setSaldo(double saldo) {
    	this.saldo = saldo;
    }

}
