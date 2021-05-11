
package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtAnno;

    @FXML
    private ComboBox<Country> CmbStato;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	int annno = Integer.parseInt(txtAnno.getText());
    	if(annno<2016 && annno>1816) {
    		model.creaGrafo(annno);
    	}else {
    		txtResult.setText("Errore !");
    	}	
    	txtResult.appendText("# Vertex : "+model.getNVertex()+"\n");
    	txtResult.appendText("# Edge : "+model.getNEdge()+"\n");
    	txtResult.appendText("# Componenti connesse : "+model.connIspector()+" \n");
    	txtResult.appendText("Edge : \n"+model.gradoCountry()+"\n");
    
    	//txtResult.appendText("Vertex :  \n"+ model.getVertex());
    	
    
    	
    }

    @FXML
    void doCalcolaStatiRaggiungibili(ActionEvent event) {
    	//model.creaGrafo(1816);
    //	txtResult.setText(" "+model.calcolaRaggiungibili(CmbStato.getValue()));
    	txtResult.clear();
    	Country input = this.CmbStato.getValue();
    	if(input == null) {
    		txtResult.appendText("ERRORE: selezionare una nazione");
    		return;
    	}
    	
    	List<Country> result = model.calcolaRaggiungibili(input);
    	if(result.size()==1) {
    		txtResult.appendText("Non vi sono nazioni raggiungibili via terra rispetto a "+input.toString());
    		return;
    	}else {
    		for(Country c: result) {
    			txtResult.appendText(c.toString()+"\n");
    		}
    	}
    }

    @FXML
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert CmbStato != null : "fx:id=\"CmbStato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

      //  txtResult.setStyle("-fx-font-family: monospace");
    }
    
	public void setcmbStato() {
	    	
	   	for(Country n : model.loadAllCountries()) {
	   		CmbStato.getItems().add(n);
	   		}
	   }
	
    public void setModel(Model model) {
    	this.model = model;
    	setcmbStato();
    }
}

