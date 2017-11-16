#version 400 core

layout (location = 0) in vec2 pos;
layout (location = 1) in vec2 uv;
layout (location = 2) in vec4 color;


out vec4 fragColor;
out vec2 fragUV;

uniform mat4 cameraMat;

void main(){
	gl_Position.xy = (cameraMat*vec4(pos,0,1)).xy;
	gl_Position.z = 0;
	gl_Position.w = 1;
	fragColor = color;
	fragUV = vec2(uv.x,1-uv.y);
}