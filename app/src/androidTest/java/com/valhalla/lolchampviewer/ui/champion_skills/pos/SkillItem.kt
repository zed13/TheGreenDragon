package com.valhalla.lolchampviewer.ui.champion_skills.pos

import android.view.View
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.text.KTextView
import com.valhalla.lolchampviewer.R
import org.hamcrest.Matcher

class SkillItem(matcher: Matcher<View>) : KRecyclerItem<SkillItem>(matcher) {
    val spellName: KTextView = KTextView { withId(R.id.spell_name) }
    val spellDescription: KTextView = KTextView { withId(R.id.spell_description) }
}