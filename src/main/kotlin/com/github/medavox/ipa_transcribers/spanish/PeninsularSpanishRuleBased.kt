package com.github.medavox.ipa_transcribers.spanish

import com.github.medavox.ipa_transcribers.Language.PeninsularSpanish
import com.github.medavox.ipa_transcribers.Rule
import com.github.medavox.ipa_transcribers.RuleBasedTranscriber
import com.github.medavox.ipa_transcribers.spanish.PanAmericanSpanishIpaRuleBased.voicedConsonants
import com.github.medavox.ipa_transcribers.spanish.PanAmericanSpanishIpaRuleBased.normaliseAccents
import com.github.medavox.ipa_transcribers.spanish.PanAmericanSpanishIpaRuleBased.removeStressAccents

object PeninsularSpanishRuleBased: RuleBasedTranscriber<PeninsularSpanish> {
    val rules:List<Rule> = PanAmericanSpanishIpaRuleBased.rules.map{
        when(it.matcher) {
            Regex("c[ie]") -> it.copy(outputString = {"θ"})
            Regex("z$voicedConsonants") -> it.copy(outputString = {"ð"})
            Regex("z") -> it.copy(outputString = {"θ"})
            else -> it
        }
    }
    override fun transcribe(nativeText: String): String {
        return nativeText.toLowerCase().normaliseAccents().removeStressAccents().processWithRules(rules, copyVerbatim)
    }
}