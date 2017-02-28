package generalcoredemo.com.cloverstudio.generalcoredemo.entity;


import com.cloverstudio.generalcore.http.base.ReqBase;

public class ArticleResponse extends ReqBase {

	private AppArticle appArticle;

	public AppArticle getAppArticle() {
		return appArticle;
	}

	public void setAppArticle(AppArticle appArticle) {
		this.appArticle = appArticle;
	}


	
}
