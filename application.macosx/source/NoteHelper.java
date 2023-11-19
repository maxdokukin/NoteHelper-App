import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class NoteHelper extends PApplet {

//Console input;
Console output;
Fret fret;
NoteCalculator noteCalculator = new NoteCalculator();
Notes notesA = new Notes("E2", 0xbf2e1b);

public void setup(){
  
  
    
  //input  = new Console(width * 0.6, height / 12);
  
  output  = new Console(width / 13, height / 12);
  
  fret = new Fret(width / 16, height * 0.35f,  width * 0.9f, height * 0.7f);
  
  //println(noteCalculator.stringBias('G', 9));
}



public void draw(){
  
  background(218, 240, 230);
  
  //input.show();
  output.show();
  fret.show();
  
  notesA.show();
}


public void keyPressed(){
  
}

public void mouseClicked(){
  
  String not = "eBGDAE";
  int fretBias  = round((mouseX -  fret.x) / fret.fretStp + 0.5f);  
  int cStr = 0;
    
  if((mouseX > fret.x - 15 && mouseX < fret.x + fret.w) && (mouseY > fret.y - 10 && mouseY < fret.y + fret.h)){
    
    for(;cStr < 6 && !(fret.y + fret.strStp * cStr - 10 < mouseY && fret.y + fret.strStp * cStr + 10 > mouseY); cStr++);
   
    cStr = constrain(cStr, 0, 5);
    fretBias = constrain(fretBias, 0, 25);
    
    notesA.update(noteCalculator.locateOnFret(noteCalculator.stringBias(not.charAt(cStr), fretBias)));
    
    output.input = noteCalculator.stringBias(not.charAt(cStr), fretBias);
  }
}



  
  
class Console{
  
  float x, y;
    
  String input = "E2";
  
  Console(float x, float y){

    this.x = x;
    this.y = y;
  }  
  
  public void show(){
    
    int textHeight = height / 8;
    
    textSize(textHeight);
    stroke(28, 191, 13);
    strokeWeight(1);
    
    text(input, x, y + textHeight);
    
    stroke(0);
    noFill();
    
    rect(x - 10, y, textWidth(input) + 10, textHeight + 10);
  }  
}
class Fret{
    
  float x, y, w, h;
  
  float fretStp, strStp;
  
  String strings = "eBGDAE";
  
  Fret(float x, float y, float w, float h){
    
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;

    this.strStp = h / 6;
    this.fretStp =  this.w / 25;
  }
  
  
  public void show(){
            
    drawFrets();
    
    drawStrings();
  }
  
  
  public void drawStrings(){
        
    stroke(188, 201, 111);
    strokeWeight(1);
    fill(207, 50, 94);
    textSize(strStp / 2);
  
    for(int i = 0; i < 6; i++){
      
      strokeWeight(map(i, 0, 5, 1, 4));
      line(x, y + strStp * i, x + w, y + strStp * i);  
      
      text(strings.charAt(i), x - 20, y + strStp * i + 5);
    }
  }
  
  
  public void drawFrets(){
        
    stroke(120, 100, 64);
    strokeWeight(1);
    fill(93, 48, 133);
    textSize(fretStp / 2);
      
    for(int i = 0; x + fretStp * i <= x + w; i++){
      
      line(x + fretStp * i, y - 1, x + fretStp * i, y + strStp * 5 + 1);  
      
      if(x + fretStp * i - textWidth(Integer.toString(i + 1)) / 2 + fretStp / 2 <= x + w) 
        text(i + 1, x + fretStp * i - textWidth(Integer.toString(i + 1)) / 2 + fretStp / 2, y - 7);
    }
      
  }
}
class NoteCalculator{
  
  String noteSeq = "A;A#;B:C;C#;D;D#;E;F;F#;G;G#;";
  
  boolean calculate = false;  
  
  public String stringBias(char note, int number){  //находит ноту на ладу опредененной струны
    
    char orStr = note;
    int nmb = number, pos = 0, octaveJnc = 0;
    
    if(note == 'e') {
      
      orStr = 'E';
      octaveJnc += 2;
    }
    else if(note == 'D' || note == 'G' || note == 'B')
      octaveJnc ++;
      
      
        
    while(noteSeq.charAt(pos) != orStr && pos < 28) //find reference note
      pos++;
            
    while(nmb != 0){
      
      if(nmb > 0){
        
        while(noteSeq.charAt(pos) != ';' && noteSeq.charAt(pos) != ':')
          pos++;
        
        pos++;
        nmb--;
      }
            
      if(pos >= noteSeq.length() - 2) //go back to the first note
        pos = 0;
        
      if(pos > 1 && noteSeq.charAt(pos - 1) == ':') //octave changed
        octaveJnc ++;
    }
        
    return formatNote(pos, octaveJnc);
  }
  
  public String formatNote(int p, int oj){ //форматирует и возвращает ноту в нужном формате
        
    String dat = Character.toString(noteSeq.charAt(p));

    if(noteSeq.charAt(p + 1) == '#')
      dat += "#";
      
    dat += oj + 2;
          
    return dat;
  }
  
  
  public int[] locateOnFret(String note){
    
    int[] stringPos = new int[6];
    
    stringPos[0] = noteDifference("E4", note);
    stringPos[1] = noteDifference("B3", note);
    stringPos[2] = noteDifference("G3", note);
    stringPos[3] = noteDifference("D3", note);
    stringPos[4] = noteDifference("A2", note);
    stringPos[5] = noteDifference("E2", note);
    
    return stringPos;
  }
  

  public int noteDifference(String note1, String note2){ //ищет разницу между двумя нотами
 
    int realOctChange = extractOctave(note2) - extractOctave(note1); //экструзия октав и нахождение разницы между ними    
      
    if(note1.charAt(1) == '#')     // убирание числа октавы (больше не нужно в String, было экструзировано в int)
      note1 = note1.substring(0, 2);
    else
      note1 = note1.substring(0, 1);
      
    if(note2.charAt(1) == '#')     // убирание числа октавы (больше не нужно в String, было экструзировано в int)
      note2 = note2.substring(0, 2);
    else
      note2 = note2.substring(0, 1);
    
    if(note1.equals("B"))         //добавление ; или : к введенным нотам (для того чтобы различались A; и A#;)
      note1 += ':';    
    else
      note1 += ';';
      
    if(note2.equals("B"))         //добавление ; или : к введенным нотам (для того чтобы различались A; и A#;)
      note2 += ':';    
    else
      note2 += ';';
        
        
    int posA = noteSeq.indexOf(note1), steps = 0, octaveJnc = 0;
    String tmp = noteSeq.substring(posA);
          
    while(tmp.indexOf(note2) != 0){      //смещение всего ряда пока нужная нота не будет первой в ряду
      
      int i = 0;
      while(tmp.charAt(i) != ';' && tmp.charAt(i) != ':' && i < tmp.length()){   //поиск ; или : для точного смещения
        
        i++;

        if(tmp.charAt(i) == ':')         //проверка перехода через октаву
          octaveJnc++;  
      }
      i++;
      
      if(tmp.length() - i <= 0)        //обновление ряда если он закончился
        tmp = noteSeq;
      else
        tmp = tmp.substring(i);        //смешение на ноту если ряд не закончился
      
      
      steps++; //счетчик смещений
    }

    int octBias = realOctChange - octaveJnc;  //итоговая разница октав
    steps += 12 * octBias;         //добавление итоговой разницы октав
        
    return steps;
  }
  
  
  public int extractOctave(String note){ //возвращает октаву из введенной ноты вида A#3 или A3
    
    int tmp = 0;
    
    if(note.charAt(1) == '#')
      tmp = Integer.parseInt(note.substring(2));
    else
      tmp = Integer.parseInt(note.substring(1));
      
    return tmp;
  }
      
  
}
class Notes{
    
  int[] noteLoc;
  
  int col;
  
  Notes(String note, int col){
       
    this.noteLoc = noteCalculator.locateOnFret(note);
    
    this.col = col;
  }
  
  
  public void show(){
      
    fill(86, 131, 245);
    stroke(0);
    strokeWeight(1);
    
    for(int i = 0; i < 6; i++)
      if(noteLoc[i] > 0 && noteLoc[i] <= 25)
        ellipse(fret.x + fret.fretStp * (noteLoc[i] - 0.5f), fret.y + fret.strStp * i, 5, 5);
      else if(noteLoc[i] == 0)
        ellipse(fret.x, fret.y + fret.strStp * i, 5, 5);
        
  }
  
  
  public void update(int [] newNoteLoc){
    
    noteLoc = newNoteLoc;
  }
}
  public void settings() {  size(800, 300); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "NoteHelper" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
