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
  
  
  void show(){
            
    drawFrets();
    
    drawStrings();
  }
  
  
  void drawStrings(){
        
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
  
  
  void drawFrets(){
        
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
