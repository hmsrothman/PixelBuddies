#version 400 core

in vec2 fragUV;

out vec4 color;

uniform sampler2D sampler;

void main(){
	vec4 texColor = texture(sampler,fragUV);
	if(texColor.a==0){
		discard;
	}
	color = texColor;
}