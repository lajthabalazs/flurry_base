package hu.droidium.flurry_base;

public enum LogCategory {
	MAP("Map"),
	LIFECYCLE("Lifecycle"),
	FACEBOOK("Facebook"),
	PERFORMANCE("Performance");

	private final String name;
	
	private LogCategory(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
}
