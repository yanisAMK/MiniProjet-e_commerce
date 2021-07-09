package gui;

import app.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class GwoController implements Initializable {
    //Contient la premiere ligne du fichier la premiere dans l'indexe 0 la deuxieme dans 1
    public String[] fileDimension;
    public String fileinpath;
    public String fileoutpath;
    public ArrayList<Offre> offres ;
    public ObservableList<OffreLite> tab ;
    public File input;
    public long timer;
    public FileWriter myWriter;
    public String datafile;

    @FXML   private TextField MutationrateTf;
    @FXML   private TextArea SolutionsTa;
    @FXML   private Label filenamelable;
    @FXML   private TableView<OffreLite> Litetable;
    @FXML   private TableColumn<OffreLite,String> Cprice ;
    @FXML   private TableColumn<OffreLite,String> CnbObjects ;
    @FXML   private TextField PopulationSizeTf;
    @FXML   private TextField MaxIterationsTf;
    @FXML   private Button SolveBtn;
    @FXML   private Label Savinglabel;

    //generes un arraylist d'offres
    //remplis la table pour l'interface
    public void readfile(File file) throws IOException {
        offres = new ArrayList<Offre>();
        tab = FXCollections.observableArrayList();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        if ((line = br.readLine())!= null){
            line = line.strip();
            fileDimension = line.split(" ");
        }
        String[] tmp;
        while((line = br.readLine()) != null){
            line = line.strip();
            tmp= line.split(" ",2);
            Offre ofrtmp = new Offre(tmp[0]) ;
            ArrayList<String> objects = new ArrayList<String>(Arrays.asList(tmp[1].split(" ")));
            OffreLite t = new OffreLite(tmp[0],objects.size()+"");
            tab.add(t);
            ofrtmp.addObjects(objects);
            offres.add(ofrtmp);
        }
        br.close();
        tmp= null;
        line = null;
    }

    @FXML//importer un fichier
    private void importfile() throws IOException {

        FileChooser chooser = new FileChooser();
        File f = chooser.showOpenDialog(null);
        input = f;
        if(f != null){
            filenamelable.setText("file: "+f.getName());
            readfile(f);
            Litetable.getItems().removeAll();
            Litetable.setItems(tab);
        }
    }

    //initialisation de l'interface
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Cprice.setCellValueFactory(data -> data.getValue().getPrice());
        CnbObjects.setCellValueFactory(data -> data.getValue().getSize());
        this.SolutionsTa.setEditable(false);
    }

    public void Solve() throws IOException {
        this.Savinglabel.setText("");
        this.SolutionsTa.clear();
        String SolutionString;
        int maxiter = Integer.parseInt(MaxIterationsTf.getCharacters().toString());
        int popsize = Integer.parseInt(PopulationSizeTf.getCharacters().toString());
        int mutationrate = Integer.parseInt(MutationrateTf.getCharacters().toString());
        fileinpath = input.getAbsolutePath();
        fileoutpath = input.getAbsolutePath() + "_output.txt";
        /////////////////////////////////////////////////////////////////////////////////
        WDPInstance instance = new WDPInstance(fileinpath);
        System.out.println("start");
        timer = System.currentTimeMillis();
        Solution sol = Genetic.startSearch(instance, popsize, maxiter, mutationrate);
        timer = System.currentTimeMillis() - timer;
        SolutionString = "Meilleure prix d'enchére : " + sol.getBid();

        for (Offer o: sol.getOffers(instance)) {
            SolutionString = SolutionString +"\n" + o.toString();
        }
        /////////////////////////////////////////////////////////////////////////////////

        SolutionString = SolutionString + "\nTemps d'execution : "+ timer/1000+"sec"
                        +"\nNombre maximum d'iterations: " +maxiter
                        +"\nPopulation size: "+ popsize + "Taux de mutation : "+ mutationrate;

        SolutionString =SolutionString + "\n";
        this.SolutionsTa.appendText(SolutionString);
        datafile = SolutionString;
    }

    public void savedata(){
        String path = input.getAbsolutePath() + "_solution.txt";
        try {
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(datafile);
            myWriter.close();
            Savinglabel.setText("File saved in: "+path);
            System.out.println("Data saved in" + path +".");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
