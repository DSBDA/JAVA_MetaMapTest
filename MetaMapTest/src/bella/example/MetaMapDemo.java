package bella.example;

import java.util.List;
import gov.nih.nlm.nls.metamap.Ev;
import gov.nih.nlm.nls.metamap.Mapping;
import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.metamap.Negation;
import gov.nih.nlm.nls.metamap.PCM;
import gov.nih.nlm.nls.metamap.Result;
import gov.nih.nlm.nls.metamap.Utterance;

/******************************************************************
 * 導入MetaMap java 測試程式碼      Created by Bella Lin - 2016/09/30
 * 
 * 說明:
 * 執行前須先安裝MetaMap，再依序將
 * skrmedpostctl_start.bat、mmserver14.bat啟用
 * 並保持開啟狀態，即可執行此調用程式碼。
 * 
 * 安裝說明：http://blog.csdn.net/it_wenle/article/details/51537049
 * 
 * 例外:
 * 如jar檔跑掉，請再匯入metamapjar資料夾中的兩個.jar即可。
 ******************************************************************/

public class MetaMapDemo {

	static int i = 0;

	public static void main(String[] args) throws Exception {

		// 病例內容 (範例:某病例醫囑內容)
		String input = "obstetric ultrasound19010C1none104-4-16 G2P1 NSD,LMP:2/23.PPT+ on 3/23.7+3w,vaginal spotting,sono:sac+,FP-.blighted ovum.none631" +
				"G2P1 NSD,LMP:2/23.PPT+ on 3/23.7+3w,vaginal spotting,sono:sac+,FP-.blighted ovum. GA 7+wks.blighted ovum";

		MetaMapApi api = new MetaMapApiImpl();
		System.out.println("api instanciated"); // api实例化
		List<Result> resultList = api.processCitationsFromString(input);
		Result result = resultList.get(0);
		List<Negation> negations = result.getNegationList();
		// 否定詞
		for (Negation negation : negations) {
			System.out.println("概念位置conceptposition:"
					+ negation.getConceptPositionList());
			System.out.println("概念配對concept pairs:"
					+ negation.getConceptPairList());
			System.out.println("觸發位置trigger positions: "
					+ negation.getTriggerPositionList());
			System.out.println("觸發trigger: " + negation.getTrigger());
			System.out.println("類型type: " + negation.getType());
		}

		for (Utterance utterance : result.getUtteranceList()) {
			System.out.println("【Utterance】");
			System.out.println("  Id: " + utterance.getId());
			System.out.println("  Utterance text: " + utterance.getString());
			System.out.println("  Position: " + utterance.getPosition());
			for (PCM pcm : utterance.getPCMList()) {
				System.out
						.println("-----------------Mappings-------------------------");
				for (Mapping map : pcm.getMappingList()) {
					i++;
					System.out.println("[" + i + "] Phrase:");
					System.out.println("  Map Score: " + map.getScore());
					for (Ev mapEv : map.getEvList()) {
						System.out.println("  Score: " + mapEv.getScore());
						System.out.println("  Concept Id: "
								+ mapEv.getConceptId());
						System.out.println("  Concept Name: "
								+ mapEv.getConceptName());
						System.out.println("  Preferred Name: "
								+ mapEv.getPreferredName());
						System.out.println("  Matched Words: "
								+ mapEv.getMatchedWords());
						System.out.println("  Semantic Types: "
								+ mapEv.getSemanticTypes());
						System.out
								.println("  MatchMap: " + mapEv.getMatchMap());
						System.out.println("  MatchMap alt. repr.: "
								+ mapEv.getMatchMapList());
						System.out.println("  is Head?: " + mapEv.isHead());
						System.out.println("  is Overmatch?: "
								+ mapEv.isOvermatch());
						System.out.println("  Sources: " + mapEv.getSources());
						System.out.println("  Positional Info: "
								+ mapEv.getPositionalInfo());
					}
				}
			}
		}

	}

}