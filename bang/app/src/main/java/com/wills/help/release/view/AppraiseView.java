package com.wills.help.release.view;

import com.wills.help.release.model.Appraise;

import java.util.List;

/**
 * com.wills.help.release.view
 * Created by lizhaoyong
 * 2017/1/17.
 */

public interface AppraiseView {
    void setAppraiseLabel(List<Appraise.Label> labelList);
    void appraise();
}
