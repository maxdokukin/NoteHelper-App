class NoteCalculator{
  
  String noteSeq = "A;A#;B:C;C#;D;D#;E;F;F#;G;G#;";
  
  boolean calculate = false;  
  
  String stringBias(char note, int number){  //находит ноту на ладу опредененной струны
    
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
  
  String formatNote(int p, int oj){ //форматирует и возвращает ноту в нужном формате
        
    String dat = Character.toString(noteSeq.charAt(p));

    if(noteSeq.charAt(p + 1) == '#')
      dat += "#";
      
    dat += oj + 2;
          
    return dat;
  }
  
  
  int[] locateOnFret(String note){
    
    int[] stringPos = new int[6];
    
    stringPos[0] = noteDifference("E4", note);
    stringPos[1] = noteDifference("B3", note);
    stringPos[2] = noteDifference("G3", note);
    stringPos[3] = noteDifference("D3", note);
    stringPos[4] = noteDifference("A2", note);
    stringPos[5] = noteDifference("E2", note);
    
    return stringPos;
  }
  

  int noteDifference(String note1, String note2){ //ищет разницу между двумя нотами
 
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
  
  
  int extractOctave(String note){ //возвращает октаву из введенной ноты вида A#3 или A3
    
    int tmp = 0;
    
    if(note.charAt(1) == '#')
      tmp = Integer.parseInt(note.substring(2));
    else
      tmp = Integer.parseInt(note.substring(1));
      
    return tmp;
  }
}
