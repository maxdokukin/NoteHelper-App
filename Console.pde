class Console{
  
  float x, y;
    
  String input = "E2";
  
  Console(float x, float y){

    this.x = x;
    this.y = y;
  }  
  
  void show(){
    
    int textHeight = (height - 200) / 8;
    
    textSize(textHeight);
    stroke(28, 191, 13);
    strokeWeight(1);
    fill(86, 131, 245);
    
    text(input, x, y + textHeight);
    
    stroke(0);
    noFill();
    
    rect(x - 10, y, textWidth(input) + 10, textHeight + 10);
  }  
}
