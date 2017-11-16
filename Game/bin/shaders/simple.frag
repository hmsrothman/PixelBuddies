#version 400 core

in vec2 fragUV;
in vec4 fragColor;

out vec4 color;

uniform sampler2D sampler;

void main(){
	vec4 texColor = texture(sampler,fragUV);
	color = texColor;
}