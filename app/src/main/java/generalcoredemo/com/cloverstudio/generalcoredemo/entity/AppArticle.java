/**
 * Copyright &copy; 2012-2014 <a href="https://zh-soft.com">ABS</a> All rights
 * reserved.
 */

package generalcoredemo.com.cloverstudio.generalcoredemo.entity;

import java.util.List;

/**
 * 文章Entity
 *
 * @author jincool.cao
 * @version 2013-05-15
 */
public class AppArticle extends AppPage {

    /**
     *
     */
    private static final long serialVersionUID = 2840768290666050392L;

    private String id;

    private String category_id;// 分类编号

    private String category_name;// 分类名称

    private String title; // 标题

    private String link; // 外部链接

    // private String image; // 文章图片
    private String keywords;// 关键字

    private String description;// 描述、摘要

    private String describe; // ios备选(描述、摘要)

    private String posid; // 推荐位，多选（1：首页滚动图；2：栏目页文章推荐；）

    private String articleType; // 文章类型 如(0=专题=1、连载 =2、话题=3 、 比赛=4、问答=5)

    private String articleLable; // 文章标签 如(热 、荐、火)等

    private String showModule; // 展示模型（article：文章；picture：图片；video：视频；link：链接；text：文本）

    private String isRecommend; // 是否推荐

    private String area_id; // 发布区域

    private String address; // 发布地址

    private String longitude; // 经度

    private String latitude; // 纬度

    private String parent_id; // 根据这个上级文章 查询 如(专题=1、连载 =2、话题=3 、 比赛=4、问答=5)

    private String search_type; // 查询类型 按 0 =推荐 1=热点 3=最新 4=猜你 4=行业查询

    private String isCollect; // 是否收藏 1=是 0=否

    private String collectId; // 收藏的id

    private String reason; // 发布未通过的原因

    private String adType; // 广告类型

    private String hits; // 点击数(阅读量)

    private String forwards; // 转发数（分享数）

    private String agrees; // 赞成次数

    private String opposes; // 反对次数

    private String complains; // 举报次数

    private String readtimes; // 实际阅读次数

    private String actualforwards; // 实际转发数

    private String actualagrees; // 实际赞成数

    private String actualopposes; // 实际反对数

    private String actualcomplains; // 实际举报数

    private String reviews; // 评论数

    private Integer topLevel; // 固顶位置

    private String voteFlag; // 是否添加投票，0表示不添加，1表示添加

    private Integer awardScore; // 打赏分数

    private Integer awardCount; // 打赏次数

    private String mediaAttention; // 媒体关注

    private Integer totalScore; // 获得总积分（积分）


    private List<String> imageUrls; // 图片 视频 语音 等 地址


    private List<String> keywordsList; // 关键字列表

    private List<AppArticle> listRelation; // 相关文章(所有的)


    private String paymentFlag; // 是否显示稿费，0表示“否”，1表示“是”

    private boolean opposeFlag; // 是否点踩

    private boolean agreeFlag; // 是否赞

    private String current_user_id; // 当前手机登录用户id

    private String showModuleExt; // 展示模型扩展

    private String videoUrl;//待播放视频的地址

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getShowModuleExt() {
        return showModuleExt;
    }

    public void setShowModuleExt(String showModuleExt) {
        this.showModuleExt = showModuleExt;
    }

    public String getCurrent_user_id() {
        return current_user_id;
    }

    public void setCurrent_user_id(String current_user_id) {
        this.current_user_id = current_user_id;
    }

    public boolean isOpposeFlag() {
        return opposeFlag;
    }

    public void setOpposeFlag(boolean opposeFlag) {
        this.opposeFlag = opposeFlag;
    }

    public boolean isAgreeFlag() {
        return agreeFlag;
    }

    public void setAgreeFlag(boolean agreeFlag) {
        this.agreeFlag = agreeFlag;
    }

    public String getPaymentFlag() {
        return paymentFlag;
    }

    public void setPaymentFlag(String paymentFlag) {
        this.paymentFlag = paymentFlag;
    }

    public AppArticle() {
        super();
    }

    public AppArticle(String id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /*
     * public String getImage() { return image; } public void setImage(String
     * image) { this.image = image;//CmsUtils.formatImageSrcToDb(image); }
     */

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public String getForwards() {
        return forwards;
    }

    public void setForwards(String forwards) {
        this.forwards = forwards;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getAgrees() {
        return agrees;
    }

    public void setAgrees(String agrees) {
        this.agrees = agrees;
    }

    public String getOpposes() {
        return opposes;
    }

    public void setOpposes(String opposes) {
        this.opposes = opposes;
    }

    public String getPosid() {
        return posid;
    }

    public void setPosid(String posid) {
        this.posid = posid;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public List<AppArticle> getListRelation() {
        return listRelation;
    }

    public void setListRelation(List<AppArticle> listRelation) {
        this.listRelation = listRelation;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


    public String getSearch_type() {
        return search_type;
    }

    public void setSearch_type(String search_type) {
        this.search_type = search_type;
    }

    public String getArticleLable() {
        return articleLable;
    }

    public void setArticleLable(String articleLable) {
        this.articleLable = articleLable;
    }

    public String getShowModule() {
        return showModule;
    }

    public void setShowModule(String showModule) {
        this.showModule = showModule;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public List<String> getKeywordsList() {
        return keywordsList;
    }

    public void setKeywordsList(List<String> keywordsList) {
        this.keywordsList = keywordsList;
    }


    public String getDescribe() {
        return description;
    }

    public void setDescribe(String describe) {
        this.description = describe;
    }

    public String getIdx() {
        return id;
    }

    public void setIdx(String idx) {
        this.id = idx;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    public String getComplains() {
        return complains;
    }

    public void setComplains(String complains) {
        this.complains = complains;
    }


    public String getVoteFlag() {
        return voteFlag;
    }

    public void setVoteFlag(String voteFlag) {
        this.voteFlag = voteFlag;
    }

    public Integer getAwardScore() {
        return awardScore;
    }

    public void setAwardScore(Integer awardScore) {
        this.awardScore = awardScore;
    }

    public Integer getAwardCount() {
        return awardCount;
    }

    public void setAwardCount(Integer awardCount) {
        this.awardCount = awardCount;
    }

    public String getMediaAttention() {
        return mediaAttention;
    }

    public void setMediaAttention(String mediaAttention) {
        this.mediaAttention = mediaAttention;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public String getActualforwards() {
        return actualforwards;
    }

    public void setActualforwards(String actualforwards) {
        this.actualforwards = actualforwards;
    }

    public String getActualagrees() {
        return actualagrees;
    }

    public void setActualagrees(String actualagrees) {
        this.actualagrees = actualagrees;
    }

    public String getActualopposes() {
        return actualopposes;
    }

    public void setActualopposes(String actualopposes) {
        this.actualopposes = actualopposes;
    }

    public String getActualcomplains() {
        return actualcomplains;
    }

    public void setActualcomplains(String actualcomplains) {
        this.actualcomplains = actualcomplains;
    }

    public void setReadtimes(String readtimes) {
        this.readtimes = readtimes;
    }

    public String getReadtimes() {
        return readtimes;
    }

    public Integer getTopLevel() {
        return topLevel;
    }

    public void setTopLevel(Integer topLevel) {
        this.topLevel = topLevel;
    }

}
