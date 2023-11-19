class Notes{
    
  int[] noteLoc;
  
  int col;
  
  Notes(String note, int col){
       
    this.noteLoc = noteCalculator.locateOnFret(note);
    
    this.col = col;
  }
  
  
  void show(){
      
    fill(86, 131, 245);
    stroke(0);
    strokeWeight(1);
    
    for(int i = 0; i < 6; i++)
      if(noteLoc[i] > 0 && noteLoc[i] <= 25)
        ellipse(fret.x + fret.fretStp * (noteLoc[i] - 0.5), fret.y + fret.strStp * i, 5, 5);
      else if(noteLoc[i] == 0)
        ellipse(fret.x, fret.y + fret.strStp * i, 5, 5);
        
  }
  
  
  void update(int [] newNoteLoc){
    
    noteLoc = newNoteLoc;
  }
}
