package com.li.teaching_assistants.service;

import com.alibaba.fastjson.JSONArray;

public interface RankService {
    JSONArray getAllRank(String email);
}
