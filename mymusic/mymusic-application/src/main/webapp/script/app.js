var song;
var amp;
var button;

var volhistory = [];

function toggleSong() {
  if (song.isPlaying()) {
    song.pause();
  } else {
    song.play();
  }
}

function preload() {
  song = loadSound('./music/demo.mp3');
}

function setup() {
  createCanvas(710, 400, WEBGL);
  button = createButton('toggle');
  button.mousePressed(toggleSong);
  song.play();
  amp = new p5.Amplitude();
}

function draw(){
    background(250);
    rotateY(frameCount * 0.01);
    var vol = amp.getLevel();
    volhistory.push(vol);

    for(var j = 0; j < 5; j++){
        push();
        for(var i = 0; i < 80; i++){
            var r = map(volhistory[i], 0, 1, 10, 100);
            translate(sin(r * 0.001 + j) * 100, sin(r * 0.001 + j) * 100, i * 0.1);
//            rotateZ(frameCount * 0.002);
            push();
            sphere(8, 6, 4);
            pop();
        }
        pop();
    }
}
//  background(0);
//  var vol = amp.getLevel();
//  volhistory.push(vol);
//  stroke(255);
//  noFill();
//
//  translate(width / 2, height / 2);
//  beginShape();
//  for (var i = 0; i < 360; i++) {
//    var r = map(volhistory[i], 0, 1, 10, 100);
//    var x = r * cos(i);
//    var y = r * sin(i);
//    vertex(x, y);
//  }
//  endShape();
//
//  if (volhistory.length > 360) {
//    volhistory.splice(0, 1);
//  }
