varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_sampler2D;

void main() {
	v_color.rgba = v_color.rgba * vec4(0.8, 1.0, 0.8, 1.0).rgba;
	gl_FragColor = texture2D(u_sampler2D, v_texCoord0) * v_color;
}