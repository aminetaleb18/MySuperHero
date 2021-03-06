package com.esiea.mysuperhero.presentation.data;

import com.esiea.mysuperhero.presentation.model.RestBaseHeroResponse;
import com.esiea.mysuperhero.presentation.model.RestDetailsHeroResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static com.esiea.mysuperhero.Constants.SUPER_HERO_BASE;
import static com.esiea.mysuperhero.Constants.SUPER_HERO_IMAGE;


public interface HeroApi {

    @GET("{Id}/"+SUPER_HERO_BASE)
    Call<RestDetailsHeroResponse> getHeroDetailResponse(@Path("Id") String Id);


    @GET("{Id}/"+SUPER_HERO_IMAGE)
    Call<RestBaseHeroResponse> getHeroBaseResponse(@Path("Id") String Id);
}
