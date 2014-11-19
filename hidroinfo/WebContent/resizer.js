function onResize()
{
// browser viewport size
var w = 100;
var h = 100;
var keepAspectRatio=false;//olha eu inventado o que nao sei
// stage dimensions
var ow = 400; // your stage width
var oh = 400; // your stage height

if (keepAspectRatio)
{
    // keep aspect ratio
    var scale = Math.min(w / ow, h / oh);
    stage.scaleX = scale;
    stage.scaleY = scale;

   // adjust canvas size
   stage.canvas.width = ow * scale;
    stage.canvas.height = oh * scale;
}
else
{
    // scale to exact fit
    stage.scaleX = w / ow;
    stage.scaleY = h / oh;

    // adjust canvas size
    stage.canvas.width = ow * stage.scaleX;
    stage.canvas.height = oh * stage.scaleY;
   }

 // update the stage
stage.update()
}