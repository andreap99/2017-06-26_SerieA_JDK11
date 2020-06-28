package it.polito.tdp.seriea.model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model model = new Model();
		System.out.println(model.creaGrafo());
		System.out.println(model.connessioni("Milan"));
	}

}
