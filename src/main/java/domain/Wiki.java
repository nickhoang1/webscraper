package domain;

public class Wiki {
	
	private String title;
	
	private String summary;
	
	private String plot;
	private String cast;
	private String production;
	private String development;
	
	private String externalLinks;
	
	public Wiki() {
		super();
	}

	public Wiki(String title, String summary, String plot, String externalLinks) {
		super();
		this.title = title;
		this.summary = summary;
		this.plot = plot;
		this.externalLinks = externalLinks;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		//this.plot += plot;
		if (this.plot == null) {
			this.plot = plot;
		}
		else {	
			this.plot += plot;
		}
	}

	public String getExternalLinks() {
		return externalLinks;
	}

	public void setExternalLinks(String externalLinks) {
		this.externalLinks += externalLinks;
	}

	public String getCast() {
		return cast;
	}

	public void setCast(String cast) {
//		this.cast += cast;
		if (this.cast == null) {
			this.cast = cast;
		}
		else {	
			this.cast += cast;
		}
	}

	public String getProduction() {
		return production;
	}

	public void setProduction(String production) {
		this.production += production;
	}

	public String getDevelopment() {
		return development;
	}

	public void setDevelopment(String development) {
		if (this.development == null) {
			this.development = development;
		}
		else {	
			this.development += development;
		}
		
	}

	@Override
	public String toString() {
		return "Wiki [title=" + title + ", summary=" + summary + ", plot=" + plot + ", \ncast=" + cast + ", production="
				+ production + ", development=" + development + ", \nexternalLinks=" + externalLinks + "]";
	}


}
