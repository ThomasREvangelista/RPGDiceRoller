package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainController implements Initializable {
	//Number of dice
	@FXML
	private TextField numD4;
	@FXML
	private TextField numD6;
	@FXML
	private TextField numD8;
	@FXML
	private TextField numD10;
	@FXML
	private TextField numD12;
	@FXML
	private TextField numD100;
	@FXML
	private TextField numD20;
	@FXML
	private TextField numDQ;
	//d-variable number
	@FXML
	private TextField valueDQ;
	//Checkboxes for +/-
	@FXML
	private CheckBox d4check;
	@FXML
	private CheckBox d6check;
	@FXML
	private CheckBox d8check;
	@FXML
	private CheckBox d10check;
	@FXML
	private CheckBox d12check;
	@FXML
	private CheckBox d100check;
	@FXML
	private CheckBox d20check;
	@FXML
	private CheckBox dQcheck;
	//Sliders for advantage/disadvantage
	@FXML
	private Slider d4slide;
	@FXML
	private Slider d6slide;
	@FXML
	private Slider d8slide;
	@FXML
	private Slider d10slide;
	@FXML
	private Slider d12slide;
	@FXML
	private Slider d100slide;
	@FXML
	private Slider d20slide;
	@FXML
	private Slider dQslide;
	//Modifiers for +/-
	@FXML
	private TextField modD4;
	@FXML
	private TextField modD6;
	@FXML
	private TextField modD8;
	@FXML
	private TextField modD10;
	@FXML
	private TextField modD12;
	@FXML
	private TextField modD100;
	@FXML
	private TextField modD20;
	@FXML
	private TextField modDQ;
	//Buttons
	@FXML
	private Button d4b;
	@FXML
	private Button d6b;
	@FXML
	private Button d8b;
	@FXML
	private Button d10b;
	@FXML
	private Button d12b;
	@FXML
	private Button d100b;
	@FXML
	private Button d20b;
	@FXML
	private Button dQb;
	//Results
	@FXML
	private TextField resD4;
	@FXML
	private TextField resD6;
	@FXML
	private TextField resD8;
	@FXML
	private TextField resD10;
	@FXML
	private TextField resD12;
	@FXML
	private TextField resD100;
	@FXML
	private TextField resD20;
	@FXML
	private TextField resDQ;
	//Text Area
	@FXML
	private TextArea bigBox;
	//Reset Button
	@FXML
	private Button reset;
	@FXML
	private Button resetHistory;
	//Checkboxes for specialty needs
	@FXML
	private CheckBox triadv;
	@FXML
	private CheckBox rr12;
	@FXML
	private CheckBox rr1s;
	@FXML
	private TextField critMin;
	@FXML
	private CheckBox critActive;
	//Checkbox and TextField for total-carry:
	@FXML
	private CheckBox carryTotal;
	@FXML
	private TextField total;
	
	//Actual code stuff.
	
	@FXML
	public void rolld4(ActionEvent event){
		intermediate(numD4, null, modD4, d4check, d4slide, resD4, 4);
	}
	
	@FXML
	public void rolld6(ActionEvent event){
		intermediate(numD6, null, modD6, d6check, d6slide, resD6, 6);
	}
	
	@FXML
	public void rolld8(ActionEvent event){
		intermediate(numD8, null, modD8, d8check, d8slide, resD8, 8);
	}
	
	@FXML
	public void rolld10(ActionEvent event){
		intermediate(numD10, null, modD10, d10check, d10slide, resD10, 10);
	}
	
	@FXML
	public void rolld12(ActionEvent event){
		intermediate(numD12, null, modD12, d12check, d12slide, resD12, 12);
	}
	
	@FXML
	public void rolld100(ActionEvent event){
		intermediate(numD100, null, modD100, d100check, d100slide, resD100, 100);
	}
	
	@FXML
	public void rolld20(ActionEvent event){
		intermediate(numD20, null, modD20, d20check, d20slide, resD20, 20);
	}
	
	@FXML
	public void rolldQ(ActionEvent event){
		intermediate(numDQ, valueDQ, modDQ, dQcheck, dQslide, resDQ, 1);
	}
	
	@FXML
	public void resetTotal(ActionEvent event){
		total.setText("0");
	}
	
	public void intermediate(TextField number, TextField value, TextField modifier, CheckBox check, Slider slider, TextField result, int max){
		int num = 1;
		int mod;
		Integer res;
		String mode = "";
		int rerollthreshold = 0;
		
		//Set number of dice to roll
		if(!number.getText().isEmpty())
			if(Integer.parseInt(number.getText()) > 0)
				num = Integer.parseInt(number.getText());
		//Set max value of dice
		if(value != null)
			if(!value.getText().isEmpty())
				if(Integer.parseInt(value.getText()) > 0)
					max = Integer.parseInt(value.getText());
		//Set Modifier
		if(!modifier.getText().isEmpty())
			if(check.isSelected()){
				mod = Integer.parseInt(modifier.getText());
			}else{
				mod = Integer.parseInt(modifier.getText()) * -1;
			}
		else
			mod = 0;
		
		if(triadv.isSelected() && max == 20){
			mode = "triadv";
		}else{
			if(slider.getValue() < 0.67){
				mode = "adv";
			}else if(slider.getValue() > 1.33){
				mode = "dis";
			}else{
				mode = "norm";
			}
		}
		
		if(max != 20 && max != 100 && rr12.isSelected()){
			rerollthreshold = 2;
		}
		
		if(max == 20 && rr1s.isSelected()){
			rerollthreshold = 1;
		}
		
		System.out.println(mode);
		
		//Set result
		res = Model.roll(num, max, mod, mode, rerollthreshold);
		if((res - mod) >= Integer.parseInt(critMin.getText()) && max == 20 && critActive.isSelected()){
			result.setText("Crit!");
			Model.tracker += "\n!!!CRITICAL-HIT!!!";
		}else if((res - mod) == 1 && max == 20 && critActive.isSelected()){
			result.setText("Miss!");
			Model.tracker += "\n!!!CRITICAL-FAIL!!!";
		}else{
			result.setText(res.toString());
		}
		
		if(carryTotal.isSelected()){
			updateTotal(res);
			Model.tracker += "\nOverall Total: " + total.getText() + "\n\n";
		}else{
			Model.tracker += "\n\n";
		}
		updateTextArea(Model.tracker);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fullReset();
	}
	
	@FXML
	public void reset(ActionEvent event) {
		fullReset();
	}
	
	@FXML
	public void resetHistory(ActionEvent event) {
		bigBox.setText("");
	}
	
	public void fullReset() {
		// TODO Auto-generated method stub
		//Set Checkboxes
		d4check.setSelected(true);
		d6check.setSelected(true);
		d8check.setSelected(true);
		d10check.setSelected(true);
		d12check.setSelected(true);
		d100check.setSelected(true);
		d20check.setSelected(true);
		dQcheck.setSelected(true);
		//Set Sliders
		d4slide.setValue(1.0);
		d6slide.setValue(1.0);
		d8slide.setValue(1.0);
		d10slide.setValue(1.0);
		d12slide.setValue(1.0);
		d100slide.setValue(1.0);
		d20slide.setValue(1.0);
		dQslide.setValue(1.0);
		//Set numbers
		numD4.setText("1");
		numD6.setText("1");
		numD8.setText("1");
		numD10.setText("1");
		numD12.setText("1");
		numD100.setText("1");
		numD20.setText("1");
		numDQ.setText("1");
		//Set value
		valueDQ.setText("1");
		//Set Modifiers
		modD4.setText("0");
		modD6.setText("0");
		modD8.setText("0");
		modD10.setText("0");
		modD12.setText("0");
		modD100.setText("0");
		modD20.setText("0");
		modDQ.setText("0");
		//Set Results
		resD4.setText("");
		resD6.setText("");
		resD8.setText("");
		resD10.setText("");
		resD12.setText("");
		resD100.setText("");
		resD20.setText("");
		resDQ.setText("");
		//Set Big box Text
		bigBox.setText("    READ ME\n-------------------------\nThis is your roll history. Your most recent rolls will appear at the top.\n\nRolls that would yeild a number less than 1 have been increased to 1.\n\nThe slider bar: sliding the button left rolls with advantage, sliding the button right rolls with disadvantage, leaving/reseting the button to the center rolls normally.\n\nThe Checkboxes: leaving the boxes selected will apply a positive modifier to the roll; deselecting the boxes will apply a negative modifier to the roll.\n\nTriple-Advantage Attack: Selecting this box will take the highest of three d20 rolls when a d20 is rolled.\n\nReroll 1s on d20: Selecting this box will reroll rolls of 1 on each d20 rolled- once per die.\n\nCrit-Minimum: the number is the lowest roll on which you are capable of acheiving a critical hit on the d20. Selecting this box will toggle wether critical hits and critical misses are tracked and recorded.\n\nReroll 1 & 2 on Damage Dice: Selecting this box will reroll rolls of 1 or 2 on each non-d20 die rolled- once per die.\n\nCarry Total: When the box to the left is selected, each total from each set of rolled die henceforth shall be totaled here (useful if you roll multiple types of dice for damage). The total resets when the checkbox is toggled off.\n\nThanks for using my tool. -TRE");
		//Set Specialty Needs
		triadv.setSelected(false);
		rr12.setSelected(false);
		rr1s.setSelected(false);
		critMin.setText("20");
		critActive.setSelected(false);
		//Reset Total
		total.setText("0");
		carryTotal.setSelected(false);
	}
	
	public void updateTextArea(String tracker) {
		bigBox.setText(tracker + bigBox.getText());
				
	}
	
	public void updateTotal(Integer result) {
		total.setText(((Integer) (result + Integer.parseInt(total.getText()))).toString());
				
	}
	
}
