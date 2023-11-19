NoteHelperApp noteHelp;

void setup(){
  
  size(800, 500);
      //add showing notes on the tab
  noteHelp = new NoteHelperApp();
}



void draw(){
  
  noteHelp.frame();
}


void keyPressed(){
  
  noteHelp.newCommand();
}

void mouseClicked(){
 
  noteHelp.mouseClickedEv();
}


void mouseWheel(MouseEvent event) {
  
  noteHelp.mouseWheeldEv(event.getCount());
}
