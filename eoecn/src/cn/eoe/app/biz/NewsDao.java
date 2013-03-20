package cn.eoe.app.biz;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;

import android.content.Context;
import cn.eoe.app.config.Constants;
import cn.eoe.app.config.Urls;
import cn.eoe.app.entity.NewsJson;
import cn.eoe.app.entity.NewsMoreResponse;
import cn.eoe.app.entity.NewsResponseEntity;
import cn.eoe.app.https.HttpUtils;
import cn.eoe.app.utils.RequestCacheUtil;

public class NewsDao extends BaseDao {

	public NewsDao(Context context) {
		super(context);
	}

	private NewsResponseEntity _newsResponse;

	public NewsResponseEntity get_newsResponse() {
		return _newsResponse;
	}

	public void set_newsResponse(NewsResponseEntity _newsResponse) {
		this._newsResponse = _newsResponse;
	}

	public NewsResponseEntity mapperJson(boolean useCache) {
		// TODO Auto-generated method stub
		NewsJson newsJson;
		try {
			String result = RequestCacheUtil.getRequestContent(mContext,
					Urls.NEWS_LIST, Constants.WebSourceType.Json,
					Constants.DBContentType.Content_list, useCache);
			newsJson = mObjectMapper.readValue(result,
					new TypeReference<NewsJson>() {
					});
			if (newsJson == null) {
				return null;
			}
			this._newsResponse = newsJson.getResponse();
			return _newsResponse;

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public NewsMoreResponse getSearchResult(String keyWord) {
		NewsMoreResponse moreResponse;
		try {
			moreResponse = mObjectMapper.readValue(
					HttpUtils.getByHttpClient(mContext, Urls.BASE_SEARCH_URL
							+ "t=news&w=" + keyWord),
					new TypeReference<NewsMoreResponse>() {
					});
			if (moreResponse != null) {
				return moreResponse;
			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}

}
