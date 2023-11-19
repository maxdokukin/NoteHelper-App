Console output;
Fret fret;
NoteCalculator noteCalculator = new NoteCalculator();
Notes notesA = new Notes("E2", 0xbf2e1b);

public void setup(){
  
  size(800, 300);
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
