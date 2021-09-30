package org.xtext.example.hclscope.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.xtext.example.hclscope.services.HclScopeGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalHclScopeParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'statemachine'", "'{'", "'state'", "','", "';'", "'junction'", "'choice'", "'}'", "'entry'", "'exit'", "'entrypoint'", "'exitpoint'", "'initial'", "'history*'", "':'", "'->'", "'transition'", "'historytransition'", "'on'", "'*'", "'when'", "'['", "']'", "'('", "')'", "'.'", "'history'"
    };
    public static final int RULE_STRING=5;
    public static final int RULE_SL_COMMENT=8;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__37=37;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__11=11;
    public static final int T__33=33;
    public static final int T__12=12;
    public static final int T__34=34;
    public static final int T__13=13;
    public static final int T__35=35;
    public static final int T__14=14;
    public static final int T__36=36;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_ID=4;
    public static final int RULE_WS=9;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=6;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;

    // delegates
    // delegators


        public InternalHclScopeParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalHclScopeParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalHclScopeParser.tokenNames; }
    public String getGrammarFileName() { return "InternalHclScope.g"; }



     	private HclScopeGrammarAccess grammarAccess;

        public InternalHclScopeParser(TokenStream input, HclScopeGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "StateMachine";
       	}

       	@Override
       	protected HclScopeGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleStateMachine"
    // InternalHclScope.g:64:1: entryRuleStateMachine returns [EObject current=null] : iv_ruleStateMachine= ruleStateMachine EOF ;
    public final EObject entryRuleStateMachine() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStateMachine = null;


        try {
            // InternalHclScope.g:64:53: (iv_ruleStateMachine= ruleStateMachine EOF )
            // InternalHclScope.g:65:2: iv_ruleStateMachine= ruleStateMachine EOF
            {
             newCompositeNode(grammarAccess.getStateMachineRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStateMachine=ruleStateMachine();

            state._fsp--;

             current =iv_ruleStateMachine; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStateMachine"


    // $ANTLR start "ruleStateMachine"
    // InternalHclScope.g:71:1: ruleStateMachine returns [EObject current=null] : (otherlv_0= 'statemachine' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'state' ( (lv_states_4_0= ruleState ) ) (otherlv_5= ',' ( (lv_states_6_0= ruleState ) ) )* otherlv_7= ';' )* ( (lv_initialtransition_8_0= ruleInitialTransition ) )? (otherlv_9= 'junction' ( (lv_junction_10_0= ruleJunction ) ) (otherlv_11= ',' ( (lv_junction_12_0= ruleJunction ) ) )* otherlv_13= ';' )* (otherlv_14= 'choice' ( (lv_choice_15_0= ruleChoice ) ) (otherlv_16= ',' ( (lv_choice_17_0= ruleChoice ) ) )* otherlv_18= ';' )* ( (lv_transition_19_0= ruleTransition ) )* otherlv_20= '}' otherlv_21= ';' ) ;
    public final EObject ruleStateMachine() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token otherlv_20=null;
        Token otherlv_21=null;
        EObject lv_states_4_0 = null;

        EObject lv_states_6_0 = null;

        EObject lv_initialtransition_8_0 = null;

        EObject lv_junction_10_0 = null;

        EObject lv_junction_12_0 = null;

        EObject lv_choice_15_0 = null;

        EObject lv_choice_17_0 = null;

        EObject lv_transition_19_0 = null;



        	enterRule();

        try {
            // InternalHclScope.g:77:2: ( (otherlv_0= 'statemachine' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'state' ( (lv_states_4_0= ruleState ) ) (otherlv_5= ',' ( (lv_states_6_0= ruleState ) ) )* otherlv_7= ';' )* ( (lv_initialtransition_8_0= ruleInitialTransition ) )? (otherlv_9= 'junction' ( (lv_junction_10_0= ruleJunction ) ) (otherlv_11= ',' ( (lv_junction_12_0= ruleJunction ) ) )* otherlv_13= ';' )* (otherlv_14= 'choice' ( (lv_choice_15_0= ruleChoice ) ) (otherlv_16= ',' ( (lv_choice_17_0= ruleChoice ) ) )* otherlv_18= ';' )* ( (lv_transition_19_0= ruleTransition ) )* otherlv_20= '}' otherlv_21= ';' ) )
            // InternalHclScope.g:78:2: (otherlv_0= 'statemachine' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'state' ( (lv_states_4_0= ruleState ) ) (otherlv_5= ',' ( (lv_states_6_0= ruleState ) ) )* otherlv_7= ';' )* ( (lv_initialtransition_8_0= ruleInitialTransition ) )? (otherlv_9= 'junction' ( (lv_junction_10_0= ruleJunction ) ) (otherlv_11= ',' ( (lv_junction_12_0= ruleJunction ) ) )* otherlv_13= ';' )* (otherlv_14= 'choice' ( (lv_choice_15_0= ruleChoice ) ) (otherlv_16= ',' ( (lv_choice_17_0= ruleChoice ) ) )* otherlv_18= ';' )* ( (lv_transition_19_0= ruleTransition ) )* otherlv_20= '}' otherlv_21= ';' )
            {
            // InternalHclScope.g:78:2: (otherlv_0= 'statemachine' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'state' ( (lv_states_4_0= ruleState ) ) (otherlv_5= ',' ( (lv_states_6_0= ruleState ) ) )* otherlv_7= ';' )* ( (lv_initialtransition_8_0= ruleInitialTransition ) )? (otherlv_9= 'junction' ( (lv_junction_10_0= ruleJunction ) ) (otherlv_11= ',' ( (lv_junction_12_0= ruleJunction ) ) )* otherlv_13= ';' )* (otherlv_14= 'choice' ( (lv_choice_15_0= ruleChoice ) ) (otherlv_16= ',' ( (lv_choice_17_0= ruleChoice ) ) )* otherlv_18= ';' )* ( (lv_transition_19_0= ruleTransition ) )* otherlv_20= '}' otherlv_21= ';' )
            // InternalHclScope.g:79:3: otherlv_0= 'statemachine' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'state' ( (lv_states_4_0= ruleState ) ) (otherlv_5= ',' ( (lv_states_6_0= ruleState ) ) )* otherlv_7= ';' )* ( (lv_initialtransition_8_0= ruleInitialTransition ) )? (otherlv_9= 'junction' ( (lv_junction_10_0= ruleJunction ) ) (otherlv_11= ',' ( (lv_junction_12_0= ruleJunction ) ) )* otherlv_13= ';' )* (otherlv_14= 'choice' ( (lv_choice_15_0= ruleChoice ) ) (otherlv_16= ',' ( (lv_choice_17_0= ruleChoice ) ) )* otherlv_18= ';' )* ( (lv_transition_19_0= ruleTransition ) )* otherlv_20= '}' otherlv_21= ';'
            {
            otherlv_0=(Token)match(input,11,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getStateMachineAccess().getStatemachineKeyword_0());
            		
            // InternalHclScope.g:83:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalHclScope.g:84:4: (lv_name_1_0= RULE_ID )
            {
            // InternalHclScope.g:84:4: (lv_name_1_0= RULE_ID )
            // InternalHclScope.g:85:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getStateMachineAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getStateMachineRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_5); 

            			newLeafNode(otherlv_2, grammarAccess.getStateMachineAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalHclScope.g:105:3: (otherlv_3= 'state' ( (lv_states_4_0= ruleState ) ) (otherlv_5= ',' ( (lv_states_6_0= ruleState ) ) )* otherlv_7= ';' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==13) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalHclScope.g:106:4: otherlv_3= 'state' ( (lv_states_4_0= ruleState ) ) (otherlv_5= ',' ( (lv_states_6_0= ruleState ) ) )* otherlv_7= ';'
            	    {
            	    otherlv_3=(Token)match(input,13,FOLLOW_3); 

            	    				newLeafNode(otherlv_3, grammarAccess.getStateMachineAccess().getStateKeyword_3_0());
            	    			
            	    // InternalHclScope.g:110:4: ( (lv_states_4_0= ruleState ) )
            	    // InternalHclScope.g:111:5: (lv_states_4_0= ruleState )
            	    {
            	    // InternalHclScope.g:111:5: (lv_states_4_0= ruleState )
            	    // InternalHclScope.g:112:6: lv_states_4_0= ruleState
            	    {

            	    						newCompositeNode(grammarAccess.getStateMachineAccess().getStatesStateParserRuleCall_3_1_0());
            	    					
            	    pushFollow(FOLLOW_6);
            	    lv_states_4_0=ruleState();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getStateMachineRule());
            	    						}
            	    						add(
            	    							current,
            	    							"states",
            	    							lv_states_4_0,
            	    							"org.xtext.example.hclscope.HclScope.State");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalHclScope.g:129:4: (otherlv_5= ',' ( (lv_states_6_0= ruleState ) ) )*
            	    loop1:
            	    do {
            	        int alt1=2;
            	        int LA1_0 = input.LA(1);

            	        if ( (LA1_0==14) ) {
            	            alt1=1;
            	        }


            	        switch (alt1) {
            	    	case 1 :
            	    	    // InternalHclScope.g:130:5: otherlv_5= ',' ( (lv_states_6_0= ruleState ) )
            	    	    {
            	    	    otherlv_5=(Token)match(input,14,FOLLOW_3); 

            	    	    					newLeafNode(otherlv_5, grammarAccess.getStateMachineAccess().getCommaKeyword_3_2_0());
            	    	    				
            	    	    // InternalHclScope.g:134:5: ( (lv_states_6_0= ruleState ) )
            	    	    // InternalHclScope.g:135:6: (lv_states_6_0= ruleState )
            	    	    {
            	    	    // InternalHclScope.g:135:6: (lv_states_6_0= ruleState )
            	    	    // InternalHclScope.g:136:7: lv_states_6_0= ruleState
            	    	    {

            	    	    							newCompositeNode(grammarAccess.getStateMachineAccess().getStatesStateParserRuleCall_3_2_1_0());
            	    	    						
            	    	    pushFollow(FOLLOW_6);
            	    	    lv_states_6_0=ruleState();

            	    	    state._fsp--;


            	    	    							if (current==null) {
            	    	    								current = createModelElementForParent(grammarAccess.getStateMachineRule());
            	    	    							}
            	    	    							add(
            	    	    								current,
            	    	    								"states",
            	    	    								lv_states_6_0,
            	    	    								"org.xtext.example.hclscope.HclScope.State");
            	    	    							afterParserOrEnumRuleCall();
            	    	    						

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop1;
            	        }
            	    } while (true);

            	    otherlv_7=(Token)match(input,15,FOLLOW_5); 

            	    				newLeafNode(otherlv_7, grammarAccess.getStateMachineAccess().getSemicolonKeyword_3_3());
            	    			

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // InternalHclScope.g:159:3: ( (lv_initialtransition_8_0= ruleInitialTransition ) )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==RULE_ID||LA3_0==23) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // InternalHclScope.g:160:4: (lv_initialtransition_8_0= ruleInitialTransition )
                    {
                    // InternalHclScope.g:160:4: (lv_initialtransition_8_0= ruleInitialTransition )
                    // InternalHclScope.g:161:5: lv_initialtransition_8_0= ruleInitialTransition
                    {

                    					newCompositeNode(grammarAccess.getStateMachineAccess().getInitialtransitionInitialTransitionParserRuleCall_4_0());
                    				
                    pushFollow(FOLLOW_7);
                    lv_initialtransition_8_0=ruleInitialTransition();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getStateMachineRule());
                    					}
                    					set(
                    						current,
                    						"initialtransition",
                    						lv_initialtransition_8_0,
                    						"org.xtext.example.hclscope.HclScope.InitialTransition");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalHclScope.g:178:3: (otherlv_9= 'junction' ( (lv_junction_10_0= ruleJunction ) ) (otherlv_11= ',' ( (lv_junction_12_0= ruleJunction ) ) )* otherlv_13= ';' )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==16) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalHclScope.g:179:4: otherlv_9= 'junction' ( (lv_junction_10_0= ruleJunction ) ) (otherlv_11= ',' ( (lv_junction_12_0= ruleJunction ) ) )* otherlv_13= ';'
            	    {
            	    otherlv_9=(Token)match(input,16,FOLLOW_3); 

            	    				newLeafNode(otherlv_9, grammarAccess.getStateMachineAccess().getJunctionKeyword_5_0());
            	    			
            	    // InternalHclScope.g:183:4: ( (lv_junction_10_0= ruleJunction ) )
            	    // InternalHclScope.g:184:5: (lv_junction_10_0= ruleJunction )
            	    {
            	    // InternalHclScope.g:184:5: (lv_junction_10_0= ruleJunction )
            	    // InternalHclScope.g:185:6: lv_junction_10_0= ruleJunction
            	    {

            	    						newCompositeNode(grammarAccess.getStateMachineAccess().getJunctionJunctionParserRuleCall_5_1_0());
            	    					
            	    pushFollow(FOLLOW_6);
            	    lv_junction_10_0=ruleJunction();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getStateMachineRule());
            	    						}
            	    						add(
            	    							current,
            	    							"junction",
            	    							lv_junction_10_0,
            	    							"org.xtext.example.hclscope.HclScope.Junction");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalHclScope.g:202:4: (otherlv_11= ',' ( (lv_junction_12_0= ruleJunction ) ) )*
            	    loop4:
            	    do {
            	        int alt4=2;
            	        int LA4_0 = input.LA(1);

            	        if ( (LA4_0==14) ) {
            	            alt4=1;
            	        }


            	        switch (alt4) {
            	    	case 1 :
            	    	    // InternalHclScope.g:203:5: otherlv_11= ',' ( (lv_junction_12_0= ruleJunction ) )
            	    	    {
            	    	    otherlv_11=(Token)match(input,14,FOLLOW_3); 

            	    	    					newLeafNode(otherlv_11, grammarAccess.getStateMachineAccess().getCommaKeyword_5_2_0());
            	    	    				
            	    	    // InternalHclScope.g:207:5: ( (lv_junction_12_0= ruleJunction ) )
            	    	    // InternalHclScope.g:208:6: (lv_junction_12_0= ruleJunction )
            	    	    {
            	    	    // InternalHclScope.g:208:6: (lv_junction_12_0= ruleJunction )
            	    	    // InternalHclScope.g:209:7: lv_junction_12_0= ruleJunction
            	    	    {

            	    	    							newCompositeNode(grammarAccess.getStateMachineAccess().getJunctionJunctionParserRuleCall_5_2_1_0());
            	    	    						
            	    	    pushFollow(FOLLOW_6);
            	    	    lv_junction_12_0=ruleJunction();

            	    	    state._fsp--;


            	    	    							if (current==null) {
            	    	    								current = createModelElementForParent(grammarAccess.getStateMachineRule());
            	    	    							}
            	    	    							add(
            	    	    								current,
            	    	    								"junction",
            	    	    								lv_junction_12_0,
            	    	    								"org.xtext.example.hclscope.HclScope.Junction");
            	    	    							afterParserOrEnumRuleCall();
            	    	    						

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop4;
            	        }
            	    } while (true);

            	    otherlv_13=(Token)match(input,15,FOLLOW_7); 

            	    				newLeafNode(otherlv_13, grammarAccess.getStateMachineAccess().getSemicolonKeyword_5_3());
            	    			

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            // InternalHclScope.g:232:3: (otherlv_14= 'choice' ( (lv_choice_15_0= ruleChoice ) ) (otherlv_16= ',' ( (lv_choice_17_0= ruleChoice ) ) )* otherlv_18= ';' )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==17) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalHclScope.g:233:4: otherlv_14= 'choice' ( (lv_choice_15_0= ruleChoice ) ) (otherlv_16= ',' ( (lv_choice_17_0= ruleChoice ) ) )* otherlv_18= ';'
            	    {
            	    otherlv_14=(Token)match(input,17,FOLLOW_3); 

            	    				newLeafNode(otherlv_14, grammarAccess.getStateMachineAccess().getChoiceKeyword_6_0());
            	    			
            	    // InternalHclScope.g:237:4: ( (lv_choice_15_0= ruleChoice ) )
            	    // InternalHclScope.g:238:5: (lv_choice_15_0= ruleChoice )
            	    {
            	    // InternalHclScope.g:238:5: (lv_choice_15_0= ruleChoice )
            	    // InternalHclScope.g:239:6: lv_choice_15_0= ruleChoice
            	    {

            	    						newCompositeNode(grammarAccess.getStateMachineAccess().getChoiceChoiceParserRuleCall_6_1_0());
            	    					
            	    pushFollow(FOLLOW_6);
            	    lv_choice_15_0=ruleChoice();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getStateMachineRule());
            	    						}
            	    						add(
            	    							current,
            	    							"choice",
            	    							lv_choice_15_0,
            	    							"org.xtext.example.hclscope.HclScope.Choice");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalHclScope.g:256:4: (otherlv_16= ',' ( (lv_choice_17_0= ruleChoice ) ) )*
            	    loop6:
            	    do {
            	        int alt6=2;
            	        int LA6_0 = input.LA(1);

            	        if ( (LA6_0==14) ) {
            	            alt6=1;
            	        }


            	        switch (alt6) {
            	    	case 1 :
            	    	    // InternalHclScope.g:257:5: otherlv_16= ',' ( (lv_choice_17_0= ruleChoice ) )
            	    	    {
            	    	    otherlv_16=(Token)match(input,14,FOLLOW_3); 

            	    	    					newLeafNode(otherlv_16, grammarAccess.getStateMachineAccess().getCommaKeyword_6_2_0());
            	    	    				
            	    	    // InternalHclScope.g:261:5: ( (lv_choice_17_0= ruleChoice ) )
            	    	    // InternalHclScope.g:262:6: (lv_choice_17_0= ruleChoice )
            	    	    {
            	    	    // InternalHclScope.g:262:6: (lv_choice_17_0= ruleChoice )
            	    	    // InternalHclScope.g:263:7: lv_choice_17_0= ruleChoice
            	    	    {

            	    	    							newCompositeNode(grammarAccess.getStateMachineAccess().getChoiceChoiceParserRuleCall_6_2_1_0());
            	    	    						
            	    	    pushFollow(FOLLOW_6);
            	    	    lv_choice_17_0=ruleChoice();

            	    	    state._fsp--;


            	    	    							if (current==null) {
            	    	    								current = createModelElementForParent(grammarAccess.getStateMachineRule());
            	    	    							}
            	    	    							add(
            	    	    								current,
            	    	    								"choice",
            	    	    								lv_choice_17_0,
            	    	    								"org.xtext.example.hclscope.HclScope.Choice");
            	    	    							afterParserOrEnumRuleCall();
            	    	    						

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop6;
            	        }
            	    } while (true);

            	    otherlv_18=(Token)match(input,15,FOLLOW_8); 

            	    				newLeafNode(otherlv_18, grammarAccess.getStateMachineAccess().getSemicolonKeyword_6_3());
            	    			

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            // InternalHclScope.g:286:3: ( (lv_transition_19_0= ruleTransition ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==27) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalHclScope.g:287:4: (lv_transition_19_0= ruleTransition )
            	    {
            	    // InternalHclScope.g:287:4: (lv_transition_19_0= ruleTransition )
            	    // InternalHclScope.g:288:5: lv_transition_19_0= ruleTransition
            	    {

            	    					newCompositeNode(grammarAccess.getStateMachineAccess().getTransitionTransitionParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_9);
            	    lv_transition_19_0=ruleTransition();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getStateMachineRule());
            	    					}
            	    					add(
            	    						current,
            	    						"transition",
            	    						lv_transition_19_0,
            	    						"org.xtext.example.hclscope.HclScope.Transition");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            otherlv_20=(Token)match(input,18,FOLLOW_10); 

            			newLeafNode(otherlv_20, grammarAccess.getStateMachineAccess().getRightCurlyBracketKeyword_8());
            		
            otherlv_21=(Token)match(input,15,FOLLOW_2); 

            			newLeafNode(otherlv_21, grammarAccess.getStateMachineAccess().getSemicolonKeyword_9());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStateMachine"


    // $ANTLR start "entryRuleState"
    // InternalHclScope.g:317:1: entryRuleState returns [EObject current=null] : iv_ruleState= ruleState EOF ;
    public final EObject entryRuleState() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleState = null;


        try {
            // InternalHclScope.g:317:46: (iv_ruleState= ruleState EOF )
            // InternalHclScope.g:318:2: iv_ruleState= ruleState EOF
            {
             newCompositeNode(grammarAccess.getStateRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleState=ruleState();

            state._fsp--;

             current =iv_ruleState; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleState"


    // $ANTLR start "ruleState"
    // InternalHclScope.g:324:1: ruleState returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '{' ( (lv_internaltransition_2_0= ruleInternalTransition ) )* (otherlv_3= 'entry' otherlv_4= '{' ( (lv_entryaction_5_0= ruleEntryAction ) ) otherlv_6= '}' otherlv_7= ';' )? (otherlv_8= 'exit' otherlv_9= '{' ( (lv_exitaction_10_0= ruleExitAction ) ) otherlv_11= '}' otherlv_12= ';' )? (otherlv_13= 'entrypoint' ( (lv_entrypoint_14_0= ruleEntryPoint ) ) (otherlv_15= ',' ( (lv_entrypoint_16_0= ruleEntryPoint ) ) )* otherlv_17= ';' )* (otherlv_18= 'exitpoint' ( (lv_exitpoint_19_0= ruleExitPoint ) ) (otherlv_20= ',' ( (lv_exitpoint_21_0= ruleExitPoint ) ) )* otherlv_22= ';' )* (otherlv_23= 'state' ( (lv_states_24_0= ruleState ) ) (otherlv_25= ',' ( (lv_states_26_0= ruleState ) ) )* otherlv_27= ';' )* ( (lv_initialtransition_28_0= ruleInitialTransition ) )? (otherlv_29= 'junction' ( (lv_junction_30_0= ruleJunction ) ) (otherlv_31= ',' ( (lv_junction_32_0= ruleJunction ) ) )* otherlv_33= ';' )* (otherlv_34= 'choice' ( (lv_choice_35_0= ruleChoice ) ) (otherlv_36= ',' ( (lv_choice_37_0= ruleChoice ) ) )* otherlv_38= ';' )* ( ( (lv_transition_39_0= ruleTransition ) ) | ( (lv_historytransition_40_0= ruleHistoryTransition ) ) )* otherlv_41= '}' )? ) ;
    public final EObject ruleState() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token otherlv_17=null;
        Token otherlv_18=null;
        Token otherlv_20=null;
        Token otherlv_22=null;
        Token otherlv_23=null;
        Token otherlv_25=null;
        Token otherlv_27=null;
        Token otherlv_29=null;
        Token otherlv_31=null;
        Token otherlv_33=null;
        Token otherlv_34=null;
        Token otherlv_36=null;
        Token otherlv_38=null;
        Token otherlv_41=null;
        EObject lv_internaltransition_2_0 = null;

        EObject lv_entryaction_5_0 = null;

        EObject lv_exitaction_10_0 = null;

        EObject lv_entrypoint_14_0 = null;

        EObject lv_entrypoint_16_0 = null;

        EObject lv_exitpoint_19_0 = null;

        EObject lv_exitpoint_21_0 = null;

        EObject lv_states_24_0 = null;

        EObject lv_states_26_0 = null;

        EObject lv_initialtransition_28_0 = null;

        EObject lv_junction_30_0 = null;

        EObject lv_junction_32_0 = null;

        EObject lv_choice_35_0 = null;

        EObject lv_choice_37_0 = null;

        EObject lv_transition_39_0 = null;

        EObject lv_historytransition_40_0 = null;



        	enterRule();

        try {
            // InternalHclScope.g:330:2: ( ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '{' ( (lv_internaltransition_2_0= ruleInternalTransition ) )* (otherlv_3= 'entry' otherlv_4= '{' ( (lv_entryaction_5_0= ruleEntryAction ) ) otherlv_6= '}' otherlv_7= ';' )? (otherlv_8= 'exit' otherlv_9= '{' ( (lv_exitaction_10_0= ruleExitAction ) ) otherlv_11= '}' otherlv_12= ';' )? (otherlv_13= 'entrypoint' ( (lv_entrypoint_14_0= ruleEntryPoint ) ) (otherlv_15= ',' ( (lv_entrypoint_16_0= ruleEntryPoint ) ) )* otherlv_17= ';' )* (otherlv_18= 'exitpoint' ( (lv_exitpoint_19_0= ruleExitPoint ) ) (otherlv_20= ',' ( (lv_exitpoint_21_0= ruleExitPoint ) ) )* otherlv_22= ';' )* (otherlv_23= 'state' ( (lv_states_24_0= ruleState ) ) (otherlv_25= ',' ( (lv_states_26_0= ruleState ) ) )* otherlv_27= ';' )* ( (lv_initialtransition_28_0= ruleInitialTransition ) )? (otherlv_29= 'junction' ( (lv_junction_30_0= ruleJunction ) ) (otherlv_31= ',' ( (lv_junction_32_0= ruleJunction ) ) )* otherlv_33= ';' )* (otherlv_34= 'choice' ( (lv_choice_35_0= ruleChoice ) ) (otherlv_36= ',' ( (lv_choice_37_0= ruleChoice ) ) )* otherlv_38= ';' )* ( ( (lv_transition_39_0= ruleTransition ) ) | ( (lv_historytransition_40_0= ruleHistoryTransition ) ) )* otherlv_41= '}' )? ) )
            // InternalHclScope.g:331:2: ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '{' ( (lv_internaltransition_2_0= ruleInternalTransition ) )* (otherlv_3= 'entry' otherlv_4= '{' ( (lv_entryaction_5_0= ruleEntryAction ) ) otherlv_6= '}' otherlv_7= ';' )? (otherlv_8= 'exit' otherlv_9= '{' ( (lv_exitaction_10_0= ruleExitAction ) ) otherlv_11= '}' otherlv_12= ';' )? (otherlv_13= 'entrypoint' ( (lv_entrypoint_14_0= ruleEntryPoint ) ) (otherlv_15= ',' ( (lv_entrypoint_16_0= ruleEntryPoint ) ) )* otherlv_17= ';' )* (otherlv_18= 'exitpoint' ( (lv_exitpoint_19_0= ruleExitPoint ) ) (otherlv_20= ',' ( (lv_exitpoint_21_0= ruleExitPoint ) ) )* otherlv_22= ';' )* (otherlv_23= 'state' ( (lv_states_24_0= ruleState ) ) (otherlv_25= ',' ( (lv_states_26_0= ruleState ) ) )* otherlv_27= ';' )* ( (lv_initialtransition_28_0= ruleInitialTransition ) )? (otherlv_29= 'junction' ( (lv_junction_30_0= ruleJunction ) ) (otherlv_31= ',' ( (lv_junction_32_0= ruleJunction ) ) )* otherlv_33= ';' )* (otherlv_34= 'choice' ( (lv_choice_35_0= ruleChoice ) ) (otherlv_36= ',' ( (lv_choice_37_0= ruleChoice ) ) )* otherlv_38= ';' )* ( ( (lv_transition_39_0= ruleTransition ) ) | ( (lv_historytransition_40_0= ruleHistoryTransition ) ) )* otherlv_41= '}' )? )
            {
            // InternalHclScope.g:331:2: ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '{' ( (lv_internaltransition_2_0= ruleInternalTransition ) )* (otherlv_3= 'entry' otherlv_4= '{' ( (lv_entryaction_5_0= ruleEntryAction ) ) otherlv_6= '}' otherlv_7= ';' )? (otherlv_8= 'exit' otherlv_9= '{' ( (lv_exitaction_10_0= ruleExitAction ) ) otherlv_11= '}' otherlv_12= ';' )? (otherlv_13= 'entrypoint' ( (lv_entrypoint_14_0= ruleEntryPoint ) ) (otherlv_15= ',' ( (lv_entrypoint_16_0= ruleEntryPoint ) ) )* otherlv_17= ';' )* (otherlv_18= 'exitpoint' ( (lv_exitpoint_19_0= ruleExitPoint ) ) (otherlv_20= ',' ( (lv_exitpoint_21_0= ruleExitPoint ) ) )* otherlv_22= ';' )* (otherlv_23= 'state' ( (lv_states_24_0= ruleState ) ) (otherlv_25= ',' ( (lv_states_26_0= ruleState ) ) )* otherlv_27= ';' )* ( (lv_initialtransition_28_0= ruleInitialTransition ) )? (otherlv_29= 'junction' ( (lv_junction_30_0= ruleJunction ) ) (otherlv_31= ',' ( (lv_junction_32_0= ruleJunction ) ) )* otherlv_33= ';' )* (otherlv_34= 'choice' ( (lv_choice_35_0= ruleChoice ) ) (otherlv_36= ',' ( (lv_choice_37_0= ruleChoice ) ) )* otherlv_38= ';' )* ( ( (lv_transition_39_0= ruleTransition ) ) | ( (lv_historytransition_40_0= ruleHistoryTransition ) ) )* otherlv_41= '}' )? )
            // InternalHclScope.g:332:3: ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '{' ( (lv_internaltransition_2_0= ruleInternalTransition ) )* (otherlv_3= 'entry' otherlv_4= '{' ( (lv_entryaction_5_0= ruleEntryAction ) ) otherlv_6= '}' otherlv_7= ';' )? (otherlv_8= 'exit' otherlv_9= '{' ( (lv_exitaction_10_0= ruleExitAction ) ) otherlv_11= '}' otherlv_12= ';' )? (otherlv_13= 'entrypoint' ( (lv_entrypoint_14_0= ruleEntryPoint ) ) (otherlv_15= ',' ( (lv_entrypoint_16_0= ruleEntryPoint ) ) )* otherlv_17= ';' )* (otherlv_18= 'exitpoint' ( (lv_exitpoint_19_0= ruleExitPoint ) ) (otherlv_20= ',' ( (lv_exitpoint_21_0= ruleExitPoint ) ) )* otherlv_22= ';' )* (otherlv_23= 'state' ( (lv_states_24_0= ruleState ) ) (otherlv_25= ',' ( (lv_states_26_0= ruleState ) ) )* otherlv_27= ';' )* ( (lv_initialtransition_28_0= ruleInitialTransition ) )? (otherlv_29= 'junction' ( (lv_junction_30_0= ruleJunction ) ) (otherlv_31= ',' ( (lv_junction_32_0= ruleJunction ) ) )* otherlv_33= ';' )* (otherlv_34= 'choice' ( (lv_choice_35_0= ruleChoice ) ) (otherlv_36= ',' ( (lv_choice_37_0= ruleChoice ) ) )* otherlv_38= ';' )* ( ( (lv_transition_39_0= ruleTransition ) ) | ( (lv_historytransition_40_0= ruleHistoryTransition ) ) )* otherlv_41= '}' )?
            {
            // InternalHclScope.g:332:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalHclScope.g:333:4: (lv_name_0_0= RULE_ID )
            {
            // InternalHclScope.g:333:4: (lv_name_0_0= RULE_ID )
            // InternalHclScope.g:334:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_11); 

            					newLeafNode(lv_name_0_0, grammarAccess.getStateAccess().getNameIDTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getStateRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_0_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            // InternalHclScope.g:350:3: (otherlv_1= '{' ( (lv_internaltransition_2_0= ruleInternalTransition ) )* (otherlv_3= 'entry' otherlv_4= '{' ( (lv_entryaction_5_0= ruleEntryAction ) ) otherlv_6= '}' otherlv_7= ';' )? (otherlv_8= 'exit' otherlv_9= '{' ( (lv_exitaction_10_0= ruleExitAction ) ) otherlv_11= '}' otherlv_12= ';' )? (otherlv_13= 'entrypoint' ( (lv_entrypoint_14_0= ruleEntryPoint ) ) (otherlv_15= ',' ( (lv_entrypoint_16_0= ruleEntryPoint ) ) )* otherlv_17= ';' )* (otherlv_18= 'exitpoint' ( (lv_exitpoint_19_0= ruleExitPoint ) ) (otherlv_20= ',' ( (lv_exitpoint_21_0= ruleExitPoint ) ) )* otherlv_22= ';' )* (otherlv_23= 'state' ( (lv_states_24_0= ruleState ) ) (otherlv_25= ',' ( (lv_states_26_0= ruleState ) ) )* otherlv_27= ';' )* ( (lv_initialtransition_28_0= ruleInitialTransition ) )? (otherlv_29= 'junction' ( (lv_junction_30_0= ruleJunction ) ) (otherlv_31= ',' ( (lv_junction_32_0= ruleJunction ) ) )* otherlv_33= ';' )* (otherlv_34= 'choice' ( (lv_choice_35_0= ruleChoice ) ) (otherlv_36= ',' ( (lv_choice_37_0= ruleChoice ) ) )* otherlv_38= ';' )* ( ( (lv_transition_39_0= ruleTransition ) ) | ( (lv_historytransition_40_0= ruleHistoryTransition ) ) )* otherlv_41= '}' )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==12) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalHclScope.g:351:4: otherlv_1= '{' ( (lv_internaltransition_2_0= ruleInternalTransition ) )* (otherlv_3= 'entry' otherlv_4= '{' ( (lv_entryaction_5_0= ruleEntryAction ) ) otherlv_6= '}' otherlv_7= ';' )? (otherlv_8= 'exit' otherlv_9= '{' ( (lv_exitaction_10_0= ruleExitAction ) ) otherlv_11= '}' otherlv_12= ';' )? (otherlv_13= 'entrypoint' ( (lv_entrypoint_14_0= ruleEntryPoint ) ) (otherlv_15= ',' ( (lv_entrypoint_16_0= ruleEntryPoint ) ) )* otherlv_17= ';' )* (otherlv_18= 'exitpoint' ( (lv_exitpoint_19_0= ruleExitPoint ) ) (otherlv_20= ',' ( (lv_exitpoint_21_0= ruleExitPoint ) ) )* otherlv_22= ';' )* (otherlv_23= 'state' ( (lv_states_24_0= ruleState ) ) (otherlv_25= ',' ( (lv_states_26_0= ruleState ) ) )* otherlv_27= ';' )* ( (lv_initialtransition_28_0= ruleInitialTransition ) )? (otherlv_29= 'junction' ( (lv_junction_30_0= ruleJunction ) ) (otherlv_31= ',' ( (lv_junction_32_0= ruleJunction ) ) )* otherlv_33= ';' )* (otherlv_34= 'choice' ( (lv_choice_35_0= ruleChoice ) ) (otherlv_36= ',' ( (lv_choice_37_0= ruleChoice ) ) )* otherlv_38= ';' )* ( ( (lv_transition_39_0= ruleTransition ) ) | ( (lv_historytransition_40_0= ruleHistoryTransition ) ) )* otherlv_41= '}'
                    {
                    otherlv_1=(Token)match(input,12,FOLLOW_12); 

                    				newLeafNode(otherlv_1, grammarAccess.getStateAccess().getLeftCurlyBracketKeyword_1_0());
                    			
                    // InternalHclScope.g:355:4: ( (lv_internaltransition_2_0= ruleInternalTransition ) )*
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( (LA9_0==RULE_ID) ) {
                            int LA9_2 = input.LA(2);

                            if ( (LA9_2==12||(LA9_2>=14 && LA9_2<=15)||LA9_2==29||LA9_2==31) ) {
                                alt9=1;
                            }


                        }
                        else if ( (LA9_0==12||(LA9_0>=14 && LA9_0<=15)||LA9_0==29||LA9_0==31) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // InternalHclScope.g:356:5: (lv_internaltransition_2_0= ruleInternalTransition )
                    	    {
                    	    // InternalHclScope.g:356:5: (lv_internaltransition_2_0= ruleInternalTransition )
                    	    // InternalHclScope.g:357:6: lv_internaltransition_2_0= ruleInternalTransition
                    	    {

                    	    						newCompositeNode(grammarAccess.getStateAccess().getInternaltransitionInternalTransitionParserRuleCall_1_1_0());
                    	    					
                    	    pushFollow(FOLLOW_12);
                    	    lv_internaltransition_2_0=ruleInternalTransition();

                    	    state._fsp--;


                    	    						if (current==null) {
                    	    							current = createModelElementForParent(grammarAccess.getStateRule());
                    	    						}
                    	    						add(
                    	    							current,
                    	    							"internaltransition",
                    	    							lv_internaltransition_2_0,
                    	    							"org.xtext.example.hclscope.HclScope.InternalTransition");
                    	    						afterParserOrEnumRuleCall();
                    	    					

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop9;
                        }
                    } while (true);

                    // InternalHclScope.g:374:4: (otherlv_3= 'entry' otherlv_4= '{' ( (lv_entryaction_5_0= ruleEntryAction ) ) otherlv_6= '}' otherlv_7= ';' )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0==19) ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // InternalHclScope.g:375:5: otherlv_3= 'entry' otherlv_4= '{' ( (lv_entryaction_5_0= ruleEntryAction ) ) otherlv_6= '}' otherlv_7= ';'
                            {
                            otherlv_3=(Token)match(input,19,FOLLOW_4); 

                            					newLeafNode(otherlv_3, grammarAccess.getStateAccess().getEntryKeyword_1_2_0());
                            				
                            otherlv_4=(Token)match(input,12,FOLLOW_13); 

                            					newLeafNode(otherlv_4, grammarAccess.getStateAccess().getLeftCurlyBracketKeyword_1_2_1());
                            				
                            // InternalHclScope.g:383:5: ( (lv_entryaction_5_0= ruleEntryAction ) )
                            // InternalHclScope.g:384:6: (lv_entryaction_5_0= ruleEntryAction )
                            {
                            // InternalHclScope.g:384:6: (lv_entryaction_5_0= ruleEntryAction )
                            // InternalHclScope.g:385:7: lv_entryaction_5_0= ruleEntryAction
                            {

                            							newCompositeNode(grammarAccess.getStateAccess().getEntryactionEntryActionParserRuleCall_1_2_2_0());
                            						
                            pushFollow(FOLLOW_14);
                            lv_entryaction_5_0=ruleEntryAction();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getStateRule());
                            							}
                            							add(
                            								current,
                            								"entryaction",
                            								lv_entryaction_5_0,
                            								"org.xtext.example.hclscope.HclScope.EntryAction");
                            							afterParserOrEnumRuleCall();
                            						

                            }


                            }

                            otherlv_6=(Token)match(input,18,FOLLOW_10); 

                            					newLeafNode(otherlv_6, grammarAccess.getStateAccess().getRightCurlyBracketKeyword_1_2_3());
                            				
                            otherlv_7=(Token)match(input,15,FOLLOW_15); 

                            					newLeafNode(otherlv_7, grammarAccess.getStateAccess().getSemicolonKeyword_1_2_4());
                            				

                            }
                            break;

                    }

                    // InternalHclScope.g:411:4: (otherlv_8= 'exit' otherlv_9= '{' ( (lv_exitaction_10_0= ruleExitAction ) ) otherlv_11= '}' otherlv_12= ';' )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==20) ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // InternalHclScope.g:412:5: otherlv_8= 'exit' otherlv_9= '{' ( (lv_exitaction_10_0= ruleExitAction ) ) otherlv_11= '}' otherlv_12= ';'
                            {
                            otherlv_8=(Token)match(input,20,FOLLOW_4); 

                            					newLeafNode(otherlv_8, grammarAccess.getStateAccess().getExitKeyword_1_3_0());
                            				
                            otherlv_9=(Token)match(input,12,FOLLOW_13); 

                            					newLeafNode(otherlv_9, grammarAccess.getStateAccess().getLeftCurlyBracketKeyword_1_3_1());
                            				
                            // InternalHclScope.g:420:5: ( (lv_exitaction_10_0= ruleExitAction ) )
                            // InternalHclScope.g:421:6: (lv_exitaction_10_0= ruleExitAction )
                            {
                            // InternalHclScope.g:421:6: (lv_exitaction_10_0= ruleExitAction )
                            // InternalHclScope.g:422:7: lv_exitaction_10_0= ruleExitAction
                            {

                            							newCompositeNode(grammarAccess.getStateAccess().getExitactionExitActionParserRuleCall_1_3_2_0());
                            						
                            pushFollow(FOLLOW_14);
                            lv_exitaction_10_0=ruleExitAction();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getStateRule());
                            							}
                            							add(
                            								current,
                            								"exitaction",
                            								lv_exitaction_10_0,
                            								"org.xtext.example.hclscope.HclScope.ExitAction");
                            							afterParserOrEnumRuleCall();
                            						

                            }


                            }

                            otherlv_11=(Token)match(input,18,FOLLOW_10); 

                            					newLeafNode(otherlv_11, grammarAccess.getStateAccess().getRightCurlyBracketKeyword_1_3_3());
                            				
                            otherlv_12=(Token)match(input,15,FOLLOW_16); 

                            					newLeafNode(otherlv_12, grammarAccess.getStateAccess().getSemicolonKeyword_1_3_4());
                            				

                            }
                            break;

                    }

                    // InternalHclScope.g:448:4: (otherlv_13= 'entrypoint' ( (lv_entrypoint_14_0= ruleEntryPoint ) ) (otherlv_15= ',' ( (lv_entrypoint_16_0= ruleEntryPoint ) ) )* otherlv_17= ';' )*
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( (LA13_0==21) ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // InternalHclScope.g:449:5: otherlv_13= 'entrypoint' ( (lv_entrypoint_14_0= ruleEntryPoint ) ) (otherlv_15= ',' ( (lv_entrypoint_16_0= ruleEntryPoint ) ) )* otherlv_17= ';'
                    	    {
                    	    otherlv_13=(Token)match(input,21,FOLLOW_3); 

                    	    					newLeafNode(otherlv_13, grammarAccess.getStateAccess().getEntrypointKeyword_1_4_0());
                    	    				
                    	    // InternalHclScope.g:453:5: ( (lv_entrypoint_14_0= ruleEntryPoint ) )
                    	    // InternalHclScope.g:454:6: (lv_entrypoint_14_0= ruleEntryPoint )
                    	    {
                    	    // InternalHclScope.g:454:6: (lv_entrypoint_14_0= ruleEntryPoint )
                    	    // InternalHclScope.g:455:7: lv_entrypoint_14_0= ruleEntryPoint
                    	    {

                    	    							newCompositeNode(grammarAccess.getStateAccess().getEntrypointEntryPointParserRuleCall_1_4_1_0());
                    	    						
                    	    pushFollow(FOLLOW_6);
                    	    lv_entrypoint_14_0=ruleEntryPoint();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getStateRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"entrypoint",
                    	    								lv_entrypoint_14_0,
                    	    								"org.xtext.example.hclscope.HclScope.EntryPoint");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }

                    	    // InternalHclScope.g:472:5: (otherlv_15= ',' ( (lv_entrypoint_16_0= ruleEntryPoint ) ) )*
                    	    loop12:
                    	    do {
                    	        int alt12=2;
                    	        int LA12_0 = input.LA(1);

                    	        if ( (LA12_0==14) ) {
                    	            alt12=1;
                    	        }


                    	        switch (alt12) {
                    	    	case 1 :
                    	    	    // InternalHclScope.g:473:6: otherlv_15= ',' ( (lv_entrypoint_16_0= ruleEntryPoint ) )
                    	    	    {
                    	    	    otherlv_15=(Token)match(input,14,FOLLOW_3); 

                    	    	    						newLeafNode(otherlv_15, grammarAccess.getStateAccess().getCommaKeyword_1_4_2_0());
                    	    	    					
                    	    	    // InternalHclScope.g:477:6: ( (lv_entrypoint_16_0= ruleEntryPoint ) )
                    	    	    // InternalHclScope.g:478:7: (lv_entrypoint_16_0= ruleEntryPoint )
                    	    	    {
                    	    	    // InternalHclScope.g:478:7: (lv_entrypoint_16_0= ruleEntryPoint )
                    	    	    // InternalHclScope.g:479:8: lv_entrypoint_16_0= ruleEntryPoint
                    	    	    {

                    	    	    								newCompositeNode(grammarAccess.getStateAccess().getEntrypointEntryPointParserRuleCall_1_4_2_1_0());
                    	    	    							
                    	    	    pushFollow(FOLLOW_6);
                    	    	    lv_entrypoint_16_0=ruleEntryPoint();

                    	    	    state._fsp--;


                    	    	    								if (current==null) {
                    	    	    									current = createModelElementForParent(grammarAccess.getStateRule());
                    	    	    								}
                    	    	    								add(
                    	    	    									current,
                    	    	    									"entrypoint",
                    	    	    									lv_entrypoint_16_0,
                    	    	    									"org.xtext.example.hclscope.HclScope.EntryPoint");
                    	    	    								afterParserOrEnumRuleCall();
                    	    	    							

                    	    	    }


                    	    	    }


                    	    	    }
                    	    	    break;

                    	    	default :
                    	    	    break loop12;
                    	        }
                    	    } while (true);

                    	    otherlv_17=(Token)match(input,15,FOLLOW_16); 

                    	    					newLeafNode(otherlv_17, grammarAccess.getStateAccess().getSemicolonKeyword_1_4_3());
                    	    				

                    	    }
                    	    break;

                    	default :
                    	    break loop13;
                        }
                    } while (true);

                    // InternalHclScope.g:502:4: (otherlv_18= 'exitpoint' ( (lv_exitpoint_19_0= ruleExitPoint ) ) (otherlv_20= ',' ( (lv_exitpoint_21_0= ruleExitPoint ) ) )* otherlv_22= ';' )*
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( (LA15_0==22) ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // InternalHclScope.g:503:5: otherlv_18= 'exitpoint' ( (lv_exitpoint_19_0= ruleExitPoint ) ) (otherlv_20= ',' ( (lv_exitpoint_21_0= ruleExitPoint ) ) )* otherlv_22= ';'
                    	    {
                    	    otherlv_18=(Token)match(input,22,FOLLOW_3); 

                    	    					newLeafNode(otherlv_18, grammarAccess.getStateAccess().getExitpointKeyword_1_5_0());
                    	    				
                    	    // InternalHclScope.g:507:5: ( (lv_exitpoint_19_0= ruleExitPoint ) )
                    	    // InternalHclScope.g:508:6: (lv_exitpoint_19_0= ruleExitPoint )
                    	    {
                    	    // InternalHclScope.g:508:6: (lv_exitpoint_19_0= ruleExitPoint )
                    	    // InternalHclScope.g:509:7: lv_exitpoint_19_0= ruleExitPoint
                    	    {

                    	    							newCompositeNode(grammarAccess.getStateAccess().getExitpointExitPointParserRuleCall_1_5_1_0());
                    	    						
                    	    pushFollow(FOLLOW_6);
                    	    lv_exitpoint_19_0=ruleExitPoint();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getStateRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"exitpoint",
                    	    								lv_exitpoint_19_0,
                    	    								"org.xtext.example.hclscope.HclScope.ExitPoint");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }

                    	    // InternalHclScope.g:526:5: (otherlv_20= ',' ( (lv_exitpoint_21_0= ruleExitPoint ) ) )*
                    	    loop14:
                    	    do {
                    	        int alt14=2;
                    	        int LA14_0 = input.LA(1);

                    	        if ( (LA14_0==14) ) {
                    	            alt14=1;
                    	        }


                    	        switch (alt14) {
                    	    	case 1 :
                    	    	    // InternalHclScope.g:527:6: otherlv_20= ',' ( (lv_exitpoint_21_0= ruleExitPoint ) )
                    	    	    {
                    	    	    otherlv_20=(Token)match(input,14,FOLLOW_3); 

                    	    	    						newLeafNode(otherlv_20, grammarAccess.getStateAccess().getCommaKeyword_1_5_2_0());
                    	    	    					
                    	    	    // InternalHclScope.g:531:6: ( (lv_exitpoint_21_0= ruleExitPoint ) )
                    	    	    // InternalHclScope.g:532:7: (lv_exitpoint_21_0= ruleExitPoint )
                    	    	    {
                    	    	    // InternalHclScope.g:532:7: (lv_exitpoint_21_0= ruleExitPoint )
                    	    	    // InternalHclScope.g:533:8: lv_exitpoint_21_0= ruleExitPoint
                    	    	    {

                    	    	    								newCompositeNode(grammarAccess.getStateAccess().getExitpointExitPointParserRuleCall_1_5_2_1_0());
                    	    	    							
                    	    	    pushFollow(FOLLOW_6);
                    	    	    lv_exitpoint_21_0=ruleExitPoint();

                    	    	    state._fsp--;


                    	    	    								if (current==null) {
                    	    	    									current = createModelElementForParent(grammarAccess.getStateRule());
                    	    	    								}
                    	    	    								add(
                    	    	    									current,
                    	    	    									"exitpoint",
                    	    	    									lv_exitpoint_21_0,
                    	    	    									"org.xtext.example.hclscope.HclScope.ExitPoint");
                    	    	    								afterParserOrEnumRuleCall();
                    	    	    							

                    	    	    }


                    	    	    }


                    	    	    }
                    	    	    break;

                    	    	default :
                    	    	    break loop14;
                    	        }
                    	    } while (true);

                    	    otherlv_22=(Token)match(input,15,FOLLOW_17); 

                    	    					newLeafNode(otherlv_22, grammarAccess.getStateAccess().getSemicolonKeyword_1_5_3());
                    	    				

                    	    }
                    	    break;

                    	default :
                    	    break loop15;
                        }
                    } while (true);

                    // InternalHclScope.g:556:4: (otherlv_23= 'state' ( (lv_states_24_0= ruleState ) ) (otherlv_25= ',' ( (lv_states_26_0= ruleState ) ) )* otherlv_27= ';' )*
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( (LA17_0==13) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // InternalHclScope.g:557:5: otherlv_23= 'state' ( (lv_states_24_0= ruleState ) ) (otherlv_25= ',' ( (lv_states_26_0= ruleState ) ) )* otherlv_27= ';'
                    	    {
                    	    otherlv_23=(Token)match(input,13,FOLLOW_3); 

                    	    					newLeafNode(otherlv_23, grammarAccess.getStateAccess().getStateKeyword_1_6_0());
                    	    				
                    	    // InternalHclScope.g:561:5: ( (lv_states_24_0= ruleState ) )
                    	    // InternalHclScope.g:562:6: (lv_states_24_0= ruleState )
                    	    {
                    	    // InternalHclScope.g:562:6: (lv_states_24_0= ruleState )
                    	    // InternalHclScope.g:563:7: lv_states_24_0= ruleState
                    	    {

                    	    							newCompositeNode(grammarAccess.getStateAccess().getStatesStateParserRuleCall_1_6_1_0());
                    	    						
                    	    pushFollow(FOLLOW_6);
                    	    lv_states_24_0=ruleState();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getStateRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"states",
                    	    								lv_states_24_0,
                    	    								"org.xtext.example.hclscope.HclScope.State");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }

                    	    // InternalHclScope.g:580:5: (otherlv_25= ',' ( (lv_states_26_0= ruleState ) ) )*
                    	    loop16:
                    	    do {
                    	        int alt16=2;
                    	        int LA16_0 = input.LA(1);

                    	        if ( (LA16_0==14) ) {
                    	            alt16=1;
                    	        }


                    	        switch (alt16) {
                    	    	case 1 :
                    	    	    // InternalHclScope.g:581:6: otherlv_25= ',' ( (lv_states_26_0= ruleState ) )
                    	    	    {
                    	    	    otherlv_25=(Token)match(input,14,FOLLOW_3); 

                    	    	    						newLeafNode(otherlv_25, grammarAccess.getStateAccess().getCommaKeyword_1_6_2_0());
                    	    	    					
                    	    	    // InternalHclScope.g:585:6: ( (lv_states_26_0= ruleState ) )
                    	    	    // InternalHclScope.g:586:7: (lv_states_26_0= ruleState )
                    	    	    {
                    	    	    // InternalHclScope.g:586:7: (lv_states_26_0= ruleState )
                    	    	    // InternalHclScope.g:587:8: lv_states_26_0= ruleState
                    	    	    {

                    	    	    								newCompositeNode(grammarAccess.getStateAccess().getStatesStateParserRuleCall_1_6_2_1_0());
                    	    	    							
                    	    	    pushFollow(FOLLOW_6);
                    	    	    lv_states_26_0=ruleState();

                    	    	    state._fsp--;


                    	    	    								if (current==null) {
                    	    	    									current = createModelElementForParent(grammarAccess.getStateRule());
                    	    	    								}
                    	    	    								add(
                    	    	    									current,
                    	    	    									"states",
                    	    	    									lv_states_26_0,
                    	    	    									"org.xtext.example.hclscope.HclScope.State");
                    	    	    								afterParserOrEnumRuleCall();
                    	    	    							

                    	    	    }


                    	    	    }


                    	    	    }
                    	    	    break;

                    	    	default :
                    	    	    break loop16;
                    	        }
                    	    } while (true);

                    	    otherlv_27=(Token)match(input,15,FOLLOW_18); 

                    	    					newLeafNode(otherlv_27, grammarAccess.getStateAccess().getSemicolonKeyword_1_6_3());
                    	    				

                    	    }
                    	    break;

                    	default :
                    	    break loop17;
                        }
                    } while (true);

                    // InternalHclScope.g:610:4: ( (lv_initialtransition_28_0= ruleInitialTransition ) )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==RULE_ID||LA18_0==23) ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // InternalHclScope.g:611:5: (lv_initialtransition_28_0= ruleInitialTransition )
                            {
                            // InternalHclScope.g:611:5: (lv_initialtransition_28_0= ruleInitialTransition )
                            // InternalHclScope.g:612:6: lv_initialtransition_28_0= ruleInitialTransition
                            {

                            						newCompositeNode(grammarAccess.getStateAccess().getInitialtransitionInitialTransitionParserRuleCall_1_7_0());
                            					
                            pushFollow(FOLLOW_19);
                            lv_initialtransition_28_0=ruleInitialTransition();

                            state._fsp--;


                            						if (current==null) {
                            							current = createModelElementForParent(grammarAccess.getStateRule());
                            						}
                            						set(
                            							current,
                            							"initialtransition",
                            							lv_initialtransition_28_0,
                            							"org.xtext.example.hclscope.HclScope.InitialTransition");
                            						afterParserOrEnumRuleCall();
                            					

                            }


                            }
                            break;

                    }

                    // InternalHclScope.g:629:4: (otherlv_29= 'junction' ( (lv_junction_30_0= ruleJunction ) ) (otherlv_31= ',' ( (lv_junction_32_0= ruleJunction ) ) )* otherlv_33= ';' )*
                    loop20:
                    do {
                        int alt20=2;
                        int LA20_0 = input.LA(1);

                        if ( (LA20_0==16) ) {
                            alt20=1;
                        }


                        switch (alt20) {
                    	case 1 :
                    	    // InternalHclScope.g:630:5: otherlv_29= 'junction' ( (lv_junction_30_0= ruleJunction ) ) (otherlv_31= ',' ( (lv_junction_32_0= ruleJunction ) ) )* otherlv_33= ';'
                    	    {
                    	    otherlv_29=(Token)match(input,16,FOLLOW_3); 

                    	    					newLeafNode(otherlv_29, grammarAccess.getStateAccess().getJunctionKeyword_1_8_0());
                    	    				
                    	    // InternalHclScope.g:634:5: ( (lv_junction_30_0= ruleJunction ) )
                    	    // InternalHclScope.g:635:6: (lv_junction_30_0= ruleJunction )
                    	    {
                    	    // InternalHclScope.g:635:6: (lv_junction_30_0= ruleJunction )
                    	    // InternalHclScope.g:636:7: lv_junction_30_0= ruleJunction
                    	    {

                    	    							newCompositeNode(grammarAccess.getStateAccess().getJunctionJunctionParserRuleCall_1_8_1_0());
                    	    						
                    	    pushFollow(FOLLOW_6);
                    	    lv_junction_30_0=ruleJunction();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getStateRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"junction",
                    	    								lv_junction_30_0,
                    	    								"org.xtext.example.hclscope.HclScope.Junction");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }

                    	    // InternalHclScope.g:653:5: (otherlv_31= ',' ( (lv_junction_32_0= ruleJunction ) ) )*
                    	    loop19:
                    	    do {
                    	        int alt19=2;
                    	        int LA19_0 = input.LA(1);

                    	        if ( (LA19_0==14) ) {
                    	            alt19=1;
                    	        }


                    	        switch (alt19) {
                    	    	case 1 :
                    	    	    // InternalHclScope.g:654:6: otherlv_31= ',' ( (lv_junction_32_0= ruleJunction ) )
                    	    	    {
                    	    	    otherlv_31=(Token)match(input,14,FOLLOW_3); 

                    	    	    						newLeafNode(otherlv_31, grammarAccess.getStateAccess().getCommaKeyword_1_8_2_0());
                    	    	    					
                    	    	    // InternalHclScope.g:658:6: ( (lv_junction_32_0= ruleJunction ) )
                    	    	    // InternalHclScope.g:659:7: (lv_junction_32_0= ruleJunction )
                    	    	    {
                    	    	    // InternalHclScope.g:659:7: (lv_junction_32_0= ruleJunction )
                    	    	    // InternalHclScope.g:660:8: lv_junction_32_0= ruleJunction
                    	    	    {

                    	    	    								newCompositeNode(grammarAccess.getStateAccess().getJunctionJunctionParserRuleCall_1_8_2_1_0());
                    	    	    							
                    	    	    pushFollow(FOLLOW_6);
                    	    	    lv_junction_32_0=ruleJunction();

                    	    	    state._fsp--;


                    	    	    								if (current==null) {
                    	    	    									current = createModelElementForParent(grammarAccess.getStateRule());
                    	    	    								}
                    	    	    								add(
                    	    	    									current,
                    	    	    									"junction",
                    	    	    									lv_junction_32_0,
                    	    	    									"org.xtext.example.hclscope.HclScope.Junction");
                    	    	    								afterParserOrEnumRuleCall();
                    	    	    							

                    	    	    }


                    	    	    }


                    	    	    }
                    	    	    break;

                    	    	default :
                    	    	    break loop19;
                    	        }
                    	    } while (true);

                    	    otherlv_33=(Token)match(input,15,FOLLOW_19); 

                    	    					newLeafNode(otherlv_33, grammarAccess.getStateAccess().getSemicolonKeyword_1_8_3());
                    	    				

                    	    }
                    	    break;

                    	default :
                    	    break loop20;
                        }
                    } while (true);

                    // InternalHclScope.g:683:4: (otherlv_34= 'choice' ( (lv_choice_35_0= ruleChoice ) ) (otherlv_36= ',' ( (lv_choice_37_0= ruleChoice ) ) )* otherlv_38= ';' )*
                    loop22:
                    do {
                        int alt22=2;
                        int LA22_0 = input.LA(1);

                        if ( (LA22_0==17) ) {
                            alt22=1;
                        }


                        switch (alt22) {
                    	case 1 :
                    	    // InternalHclScope.g:684:5: otherlv_34= 'choice' ( (lv_choice_35_0= ruleChoice ) ) (otherlv_36= ',' ( (lv_choice_37_0= ruleChoice ) ) )* otherlv_38= ';'
                    	    {
                    	    otherlv_34=(Token)match(input,17,FOLLOW_3); 

                    	    					newLeafNode(otherlv_34, grammarAccess.getStateAccess().getChoiceKeyword_1_9_0());
                    	    				
                    	    // InternalHclScope.g:688:5: ( (lv_choice_35_0= ruleChoice ) )
                    	    // InternalHclScope.g:689:6: (lv_choice_35_0= ruleChoice )
                    	    {
                    	    // InternalHclScope.g:689:6: (lv_choice_35_0= ruleChoice )
                    	    // InternalHclScope.g:690:7: lv_choice_35_0= ruleChoice
                    	    {

                    	    							newCompositeNode(grammarAccess.getStateAccess().getChoiceChoiceParserRuleCall_1_9_1_0());
                    	    						
                    	    pushFollow(FOLLOW_6);
                    	    lv_choice_35_0=ruleChoice();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getStateRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"choice",
                    	    								lv_choice_35_0,
                    	    								"org.xtext.example.hclscope.HclScope.Choice");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }

                    	    // InternalHclScope.g:707:5: (otherlv_36= ',' ( (lv_choice_37_0= ruleChoice ) ) )*
                    	    loop21:
                    	    do {
                    	        int alt21=2;
                    	        int LA21_0 = input.LA(1);

                    	        if ( (LA21_0==14) ) {
                    	            alt21=1;
                    	        }


                    	        switch (alt21) {
                    	    	case 1 :
                    	    	    // InternalHclScope.g:708:6: otherlv_36= ',' ( (lv_choice_37_0= ruleChoice ) )
                    	    	    {
                    	    	    otherlv_36=(Token)match(input,14,FOLLOW_3); 

                    	    	    						newLeafNode(otherlv_36, grammarAccess.getStateAccess().getCommaKeyword_1_9_2_0());
                    	    	    					
                    	    	    // InternalHclScope.g:712:6: ( (lv_choice_37_0= ruleChoice ) )
                    	    	    // InternalHclScope.g:713:7: (lv_choice_37_0= ruleChoice )
                    	    	    {
                    	    	    // InternalHclScope.g:713:7: (lv_choice_37_0= ruleChoice )
                    	    	    // InternalHclScope.g:714:8: lv_choice_37_0= ruleChoice
                    	    	    {

                    	    	    								newCompositeNode(grammarAccess.getStateAccess().getChoiceChoiceParserRuleCall_1_9_2_1_0());
                    	    	    							
                    	    	    pushFollow(FOLLOW_6);
                    	    	    lv_choice_37_0=ruleChoice();

                    	    	    state._fsp--;


                    	    	    								if (current==null) {
                    	    	    									current = createModelElementForParent(grammarAccess.getStateRule());
                    	    	    								}
                    	    	    								add(
                    	    	    									current,
                    	    	    									"choice",
                    	    	    									lv_choice_37_0,
                    	    	    									"org.xtext.example.hclscope.HclScope.Choice");
                    	    	    								afterParserOrEnumRuleCall();
                    	    	    							

                    	    	    }


                    	    	    }


                    	    	    }
                    	    	    break;

                    	    	default :
                    	    	    break loop21;
                    	        }
                    	    } while (true);

                    	    otherlv_38=(Token)match(input,15,FOLLOW_20); 

                    	    					newLeafNode(otherlv_38, grammarAccess.getStateAccess().getSemicolonKeyword_1_9_3());
                    	    				

                    	    }
                    	    break;

                    	default :
                    	    break loop22;
                        }
                    } while (true);

                    // InternalHclScope.g:737:4: ( ( (lv_transition_39_0= ruleTransition ) ) | ( (lv_historytransition_40_0= ruleHistoryTransition ) ) )*
                    loop23:
                    do {
                        int alt23=3;
                        int LA23_0 = input.LA(1);

                        if ( (LA23_0==27) ) {
                            alt23=1;
                        }
                        else if ( (LA23_0==28) ) {
                            alt23=2;
                        }


                        switch (alt23) {
                    	case 1 :
                    	    // InternalHclScope.g:738:5: ( (lv_transition_39_0= ruleTransition ) )
                    	    {
                    	    // InternalHclScope.g:738:5: ( (lv_transition_39_0= ruleTransition ) )
                    	    // InternalHclScope.g:739:6: (lv_transition_39_0= ruleTransition )
                    	    {
                    	    // InternalHclScope.g:739:6: (lv_transition_39_0= ruleTransition )
                    	    // InternalHclScope.g:740:7: lv_transition_39_0= ruleTransition
                    	    {

                    	    							newCompositeNode(grammarAccess.getStateAccess().getTransitionTransitionParserRuleCall_1_10_0_0());
                    	    						
                    	    pushFollow(FOLLOW_21);
                    	    lv_transition_39_0=ruleTransition();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getStateRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"transition",
                    	    								lv_transition_39_0,
                    	    								"org.xtext.example.hclscope.HclScope.Transition");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalHclScope.g:758:5: ( (lv_historytransition_40_0= ruleHistoryTransition ) )
                    	    {
                    	    // InternalHclScope.g:758:5: ( (lv_historytransition_40_0= ruleHistoryTransition ) )
                    	    // InternalHclScope.g:759:6: (lv_historytransition_40_0= ruleHistoryTransition )
                    	    {
                    	    // InternalHclScope.g:759:6: (lv_historytransition_40_0= ruleHistoryTransition )
                    	    // InternalHclScope.g:760:7: lv_historytransition_40_0= ruleHistoryTransition
                    	    {

                    	    							newCompositeNode(grammarAccess.getStateAccess().getHistorytransitionHistoryTransitionParserRuleCall_1_10_1_0());
                    	    						
                    	    pushFollow(FOLLOW_21);
                    	    lv_historytransition_40_0=ruleHistoryTransition();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getStateRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"historytransition",
                    	    								lv_historytransition_40_0,
                    	    								"org.xtext.example.hclscope.HclScope.HistoryTransition");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop23;
                        }
                    } while (true);

                    otherlv_41=(Token)match(input,18,FOLLOW_2); 

                    				newLeafNode(otherlv_41, grammarAccess.getStateAccess().getRightCurlyBracketKeyword_1_11());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleState"


    // $ANTLR start "entryRuleEntryAction"
    // InternalHclScope.g:787:1: entryRuleEntryAction returns [EObject current=null] : iv_ruleEntryAction= ruleEntryAction EOF ;
    public final EObject entryRuleEntryAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEntryAction = null;


        try {
            // InternalHclScope.g:787:52: (iv_ruleEntryAction= ruleEntryAction EOF )
            // InternalHclScope.g:788:2: iv_ruleEntryAction= ruleEntryAction EOF
            {
             newCompositeNode(grammarAccess.getEntryActionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEntryAction=ruleEntryAction();

            state._fsp--;

             current =iv_ruleEntryAction; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEntryAction"


    // $ANTLR start "ruleEntryAction"
    // InternalHclScope.g:794:1: ruleEntryAction returns [EObject current=null] : ( (lv_name_0_0= RULE_STRING ) ) ;
    public final EObject ruleEntryAction() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;


        	enterRule();

        try {
            // InternalHclScope.g:800:2: ( ( (lv_name_0_0= RULE_STRING ) ) )
            // InternalHclScope.g:801:2: ( (lv_name_0_0= RULE_STRING ) )
            {
            // InternalHclScope.g:801:2: ( (lv_name_0_0= RULE_STRING ) )
            // InternalHclScope.g:802:3: (lv_name_0_0= RULE_STRING )
            {
            // InternalHclScope.g:802:3: (lv_name_0_0= RULE_STRING )
            // InternalHclScope.g:803:4: lv_name_0_0= RULE_STRING
            {
            lv_name_0_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

            				newLeafNode(lv_name_0_0, grammarAccess.getEntryActionAccess().getNameSTRINGTerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getEntryActionRule());
            				}
            				setWithLastConsumed(
            					current,
            					"name",
            					lv_name_0_0,
            					"org.eclipse.xtext.common.Terminals.STRING");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEntryAction"


    // $ANTLR start "entryRuleExitAction"
    // InternalHclScope.g:822:1: entryRuleExitAction returns [EObject current=null] : iv_ruleExitAction= ruleExitAction EOF ;
    public final EObject entryRuleExitAction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExitAction = null;


        try {
            // InternalHclScope.g:822:51: (iv_ruleExitAction= ruleExitAction EOF )
            // InternalHclScope.g:823:2: iv_ruleExitAction= ruleExitAction EOF
            {
             newCompositeNode(grammarAccess.getExitActionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExitAction=ruleExitAction();

            state._fsp--;

             current =iv_ruleExitAction; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExitAction"


    // $ANTLR start "ruleExitAction"
    // InternalHclScope.g:829:1: ruleExitAction returns [EObject current=null] : ( (lv_name_0_0= RULE_STRING ) ) ;
    public final EObject ruleExitAction() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;


        	enterRule();

        try {
            // InternalHclScope.g:835:2: ( ( (lv_name_0_0= RULE_STRING ) ) )
            // InternalHclScope.g:836:2: ( (lv_name_0_0= RULE_STRING ) )
            {
            // InternalHclScope.g:836:2: ( (lv_name_0_0= RULE_STRING ) )
            // InternalHclScope.g:837:3: (lv_name_0_0= RULE_STRING )
            {
            // InternalHclScope.g:837:3: (lv_name_0_0= RULE_STRING )
            // InternalHclScope.g:838:4: lv_name_0_0= RULE_STRING
            {
            lv_name_0_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

            				newLeafNode(lv_name_0_0, grammarAccess.getExitActionAccess().getNameSTRINGTerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getExitActionRule());
            				}
            				setWithLastConsumed(
            					current,
            					"name",
            					lv_name_0_0,
            					"org.eclipse.xtext.common.Terminals.STRING");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExitAction"


    // $ANTLR start "entryRuleInitialState"
    // InternalHclScope.g:857:1: entryRuleInitialState returns [EObject current=null] : iv_ruleInitialState= ruleInitialState EOF ;
    public final EObject entryRuleInitialState() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInitialState = null;


        try {
            // InternalHclScope.g:857:53: (iv_ruleInitialState= ruleInitialState EOF )
            // InternalHclScope.g:858:2: iv_ruleInitialState= ruleInitialState EOF
            {
             newCompositeNode(grammarAccess.getInitialStateRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleInitialState=ruleInitialState();

            state._fsp--;

             current =iv_ruleInitialState; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInitialState"


    // $ANTLR start "ruleInitialState"
    // InternalHclScope.g:864:1: ruleInitialState returns [EObject current=null] : ( (lv_name_0_0= 'initial' ) ) ;
    public final EObject ruleInitialState() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;


        	enterRule();

        try {
            // InternalHclScope.g:870:2: ( ( (lv_name_0_0= 'initial' ) ) )
            // InternalHclScope.g:871:2: ( (lv_name_0_0= 'initial' ) )
            {
            // InternalHclScope.g:871:2: ( (lv_name_0_0= 'initial' ) )
            // InternalHclScope.g:872:3: (lv_name_0_0= 'initial' )
            {
            // InternalHclScope.g:872:3: (lv_name_0_0= 'initial' )
            // InternalHclScope.g:873:4: lv_name_0_0= 'initial'
            {
            lv_name_0_0=(Token)match(input,23,FOLLOW_2); 

            				newLeafNode(lv_name_0_0, grammarAccess.getInitialStateAccess().getNameInitialKeyword_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getInitialStateRule());
            				}
            				setWithLastConsumed(current, "name", lv_name_0_0, "initial");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInitialState"


    // $ANTLR start "entryRuleJunction"
    // InternalHclScope.g:888:1: entryRuleJunction returns [EObject current=null] : iv_ruleJunction= ruleJunction EOF ;
    public final EObject entryRuleJunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJunction = null;


        try {
            // InternalHclScope.g:888:49: (iv_ruleJunction= ruleJunction EOF )
            // InternalHclScope.g:889:2: iv_ruleJunction= ruleJunction EOF
            {
             newCompositeNode(grammarAccess.getJunctionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleJunction=ruleJunction();

            state._fsp--;

             current =iv_ruleJunction; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJunction"


    // $ANTLR start "ruleJunction"
    // InternalHclScope.g:895:1: ruleJunction returns [EObject current=null] : ( (lv_name_0_0= RULE_ID ) ) ;
    public final EObject ruleJunction() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;


        	enterRule();

        try {
            // InternalHclScope.g:901:2: ( ( (lv_name_0_0= RULE_ID ) ) )
            // InternalHclScope.g:902:2: ( (lv_name_0_0= RULE_ID ) )
            {
            // InternalHclScope.g:902:2: ( (lv_name_0_0= RULE_ID ) )
            // InternalHclScope.g:903:3: (lv_name_0_0= RULE_ID )
            {
            // InternalHclScope.g:903:3: (lv_name_0_0= RULE_ID )
            // InternalHclScope.g:904:4: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            				newLeafNode(lv_name_0_0, grammarAccess.getJunctionAccess().getNameIDTerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getJunctionRule());
            				}
            				setWithLastConsumed(
            					current,
            					"name",
            					lv_name_0_0,
            					"org.eclipse.xtext.common.Terminals.ID");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJunction"


    // $ANTLR start "entryRuleChoice"
    // InternalHclScope.g:923:1: entryRuleChoice returns [EObject current=null] : iv_ruleChoice= ruleChoice EOF ;
    public final EObject entryRuleChoice() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleChoice = null;


        try {
            // InternalHclScope.g:923:47: (iv_ruleChoice= ruleChoice EOF )
            // InternalHclScope.g:924:2: iv_ruleChoice= ruleChoice EOF
            {
             newCompositeNode(grammarAccess.getChoiceRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleChoice=ruleChoice();

            state._fsp--;

             current =iv_ruleChoice; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleChoice"


    // $ANTLR start "ruleChoice"
    // InternalHclScope.g:930:1: ruleChoice returns [EObject current=null] : ( (lv_name_0_0= RULE_ID ) ) ;
    public final EObject ruleChoice() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;


        	enterRule();

        try {
            // InternalHclScope.g:936:2: ( ( (lv_name_0_0= RULE_ID ) ) )
            // InternalHclScope.g:937:2: ( (lv_name_0_0= RULE_ID ) )
            {
            // InternalHclScope.g:937:2: ( (lv_name_0_0= RULE_ID ) )
            // InternalHclScope.g:938:3: (lv_name_0_0= RULE_ID )
            {
            // InternalHclScope.g:938:3: (lv_name_0_0= RULE_ID )
            // InternalHclScope.g:939:4: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            				newLeafNode(lv_name_0_0, grammarAccess.getChoiceAccess().getNameIDTerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getChoiceRule());
            				}
            				setWithLastConsumed(
            					current,
            					"name",
            					lv_name_0_0,
            					"org.eclipse.xtext.common.Terminals.ID");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleChoice"


    // $ANTLR start "entryRuleEntryPoint"
    // InternalHclScope.g:958:1: entryRuleEntryPoint returns [EObject current=null] : iv_ruleEntryPoint= ruleEntryPoint EOF ;
    public final EObject entryRuleEntryPoint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEntryPoint = null;


        try {
            // InternalHclScope.g:958:51: (iv_ruleEntryPoint= ruleEntryPoint EOF )
            // InternalHclScope.g:959:2: iv_ruleEntryPoint= ruleEntryPoint EOF
            {
             newCompositeNode(grammarAccess.getEntryPointRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEntryPoint=ruleEntryPoint();

            state._fsp--;

             current =iv_ruleEntryPoint; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEntryPoint"


    // $ANTLR start "ruleEntryPoint"
    // InternalHclScope.g:965:1: ruleEntryPoint returns [EObject current=null] : ( (lv_name_0_0= RULE_ID ) ) ;
    public final EObject ruleEntryPoint() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;


        	enterRule();

        try {
            // InternalHclScope.g:971:2: ( ( (lv_name_0_0= RULE_ID ) ) )
            // InternalHclScope.g:972:2: ( (lv_name_0_0= RULE_ID ) )
            {
            // InternalHclScope.g:972:2: ( (lv_name_0_0= RULE_ID ) )
            // InternalHclScope.g:973:3: (lv_name_0_0= RULE_ID )
            {
            // InternalHclScope.g:973:3: (lv_name_0_0= RULE_ID )
            // InternalHclScope.g:974:4: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            				newLeafNode(lv_name_0_0, grammarAccess.getEntryPointAccess().getNameIDTerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getEntryPointRule());
            				}
            				setWithLastConsumed(
            					current,
            					"name",
            					lv_name_0_0,
            					"org.eclipse.xtext.common.Terminals.ID");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEntryPoint"


    // $ANTLR start "entryRuleExitPoint"
    // InternalHclScope.g:993:1: entryRuleExitPoint returns [EObject current=null] : iv_ruleExitPoint= ruleExitPoint EOF ;
    public final EObject entryRuleExitPoint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExitPoint = null;


        try {
            // InternalHclScope.g:993:50: (iv_ruleExitPoint= ruleExitPoint EOF )
            // InternalHclScope.g:994:2: iv_ruleExitPoint= ruleExitPoint EOF
            {
             newCompositeNode(grammarAccess.getExitPointRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExitPoint=ruleExitPoint();

            state._fsp--;

             current =iv_ruleExitPoint; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExitPoint"


    // $ANTLR start "ruleExitPoint"
    // InternalHclScope.g:1000:1: ruleExitPoint returns [EObject current=null] : ( (lv_name_0_0= RULE_ID ) ) ;
    public final EObject ruleExitPoint() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;


        	enterRule();

        try {
            // InternalHclScope.g:1006:2: ( ( (lv_name_0_0= RULE_ID ) ) )
            // InternalHclScope.g:1007:2: ( (lv_name_0_0= RULE_ID ) )
            {
            // InternalHclScope.g:1007:2: ( (lv_name_0_0= RULE_ID ) )
            // InternalHclScope.g:1008:3: (lv_name_0_0= RULE_ID )
            {
            // InternalHclScope.g:1008:3: (lv_name_0_0= RULE_ID )
            // InternalHclScope.g:1009:4: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            				newLeafNode(lv_name_0_0, grammarAccess.getExitPointAccess().getNameIDTerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getExitPointRule());
            				}
            				setWithLastConsumed(
            					current,
            					"name",
            					lv_name_0_0,
            					"org.eclipse.xtext.common.Terminals.ID");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExitPoint"


    // $ANTLR start "entryRuleDeepHistory"
    // InternalHclScope.g:1028:1: entryRuleDeepHistory returns [EObject current=null] : iv_ruleDeepHistory= ruleDeepHistory EOF ;
    public final EObject entryRuleDeepHistory() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDeepHistory = null;


        try {
            // InternalHclScope.g:1028:52: (iv_ruleDeepHistory= ruleDeepHistory EOF )
            // InternalHclScope.g:1029:2: iv_ruleDeepHistory= ruleDeepHistory EOF
            {
             newCompositeNode(grammarAccess.getDeepHistoryRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDeepHistory=ruleDeepHistory();

            state._fsp--;

             current =iv_ruleDeepHistory; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDeepHistory"


    // $ANTLR start "ruleDeepHistory"
    // InternalHclScope.g:1035:1: ruleDeepHistory returns [EObject current=null] : ( (lv_name_0_0= 'history*' ) ) ;
    public final EObject ruleDeepHistory() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;


        	enterRule();

        try {
            // InternalHclScope.g:1041:2: ( ( (lv_name_0_0= 'history*' ) ) )
            // InternalHclScope.g:1042:2: ( (lv_name_0_0= 'history*' ) )
            {
            // InternalHclScope.g:1042:2: ( (lv_name_0_0= 'history*' ) )
            // InternalHclScope.g:1043:3: (lv_name_0_0= 'history*' )
            {
            // InternalHclScope.g:1043:3: (lv_name_0_0= 'history*' )
            // InternalHclScope.g:1044:4: lv_name_0_0= 'history*'
            {
            lv_name_0_0=(Token)match(input,24,FOLLOW_2); 

            				newLeafNode(lv_name_0_0, grammarAccess.getDeepHistoryAccess().getNameHistoryKeyword_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getDeepHistoryRule());
            				}
            				setWithLastConsumed(current, "name", lv_name_0_0, "history*");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDeepHistory"


    // $ANTLR start "entryRuleInitialTransition"
    // InternalHclScope.g:1059:1: entryRuleInitialTransition returns [EObject current=null] : iv_ruleInitialTransition= ruleInitialTransition EOF ;
    public final EObject entryRuleInitialTransition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInitialTransition = null;


        try {
            // InternalHclScope.g:1059:58: (iv_ruleInitialTransition= ruleInitialTransition EOF )
            // InternalHclScope.g:1060:2: iv_ruleInitialTransition= ruleInitialTransition EOF
            {
             newCompositeNode(grammarAccess.getInitialTransitionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleInitialTransition=ruleInitialTransition();

            state._fsp--;

             current =iv_ruleInitialTransition; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInitialTransition"


    // $ANTLR start "ruleInitialTransition"
    // InternalHclScope.g:1066:1: ruleInitialTransition returns [EObject current=null] : ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_initialstate_2_0= ruleInitialState ) ) otherlv_3= '->' ( ( ruleQualifiedName ) )? ( (lv_transitionbody_5_0= ruleTransitionBody ) ) otherlv_6= ';' ) ;
    public final EObject ruleInitialTransition() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_6=null;
        EObject lv_initialstate_2_0 = null;

        EObject lv_transitionbody_5_0 = null;



        	enterRule();

        try {
            // InternalHclScope.g:1072:2: ( ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_initialstate_2_0= ruleInitialState ) ) otherlv_3= '->' ( ( ruleQualifiedName ) )? ( (lv_transitionbody_5_0= ruleTransitionBody ) ) otherlv_6= ';' ) )
            // InternalHclScope.g:1073:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_initialstate_2_0= ruleInitialState ) ) otherlv_3= '->' ( ( ruleQualifiedName ) )? ( (lv_transitionbody_5_0= ruleTransitionBody ) ) otherlv_6= ';' )
            {
            // InternalHclScope.g:1073:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_initialstate_2_0= ruleInitialState ) ) otherlv_3= '->' ( ( ruleQualifiedName ) )? ( (lv_transitionbody_5_0= ruleTransitionBody ) ) otherlv_6= ';' )
            // InternalHclScope.g:1074:3: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_initialstate_2_0= ruleInitialState ) ) otherlv_3= '->' ( ( ruleQualifiedName ) )? ( (lv_transitionbody_5_0= ruleTransitionBody ) ) otherlv_6= ';'
            {
            // InternalHclScope.g:1074:3: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==RULE_ID) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalHclScope.g:1075:4: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':'
                    {
                    // InternalHclScope.g:1075:4: ( (lv_name_0_0= RULE_ID ) )
                    // InternalHclScope.g:1076:5: (lv_name_0_0= RULE_ID )
                    {
                    // InternalHclScope.g:1076:5: (lv_name_0_0= RULE_ID )
                    // InternalHclScope.g:1077:6: lv_name_0_0= RULE_ID
                    {
                    lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_22); 

                    						newLeafNode(lv_name_0_0, grammarAccess.getInitialTransitionAccess().getNameIDTerminalRuleCall_0_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getInitialTransitionRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"name",
                    							lv_name_0_0,
                    							"org.eclipse.xtext.common.Terminals.ID");
                    					

                    }


                    }

                    otherlv_1=(Token)match(input,25,FOLLOW_23); 

                    				newLeafNode(otherlv_1, grammarAccess.getInitialTransitionAccess().getColonKeyword_0_1());
                    			

                    }
                    break;

            }

            // InternalHclScope.g:1098:3: ( (lv_initialstate_2_0= ruleInitialState ) )
            // InternalHclScope.g:1099:4: (lv_initialstate_2_0= ruleInitialState )
            {
            // InternalHclScope.g:1099:4: (lv_initialstate_2_0= ruleInitialState )
            // InternalHclScope.g:1100:5: lv_initialstate_2_0= ruleInitialState
            {

            					newCompositeNode(grammarAccess.getInitialTransitionAccess().getInitialstateInitialStateParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_24);
            lv_initialstate_2_0=ruleInitialState();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getInitialTransitionRule());
            					}
            					set(
            						current,
            						"initialstate",
            						lv_initialstate_2_0,
            						"org.xtext.example.hclscope.HclScope.InitialState");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,26,FOLLOW_25); 

            			newLeafNode(otherlv_3, grammarAccess.getInitialTransitionAccess().getHyphenMinusGreaterThanSignKeyword_2());
            		
            // InternalHclScope.g:1121:3: ( ( ruleQualifiedName ) )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==RULE_ID||(LA26_0>=23 && LA26_0<=24)||LA26_0==37) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalHclScope.g:1122:4: ( ruleQualifiedName )
                    {
                    // InternalHclScope.g:1122:4: ( ruleQualifiedName )
                    // InternalHclScope.g:1123:5: ruleQualifiedName
                    {

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getInitialTransitionRule());
                    					}
                    				

                    					newCompositeNode(grammarAccess.getInitialTransitionAccess().getInitialtoVertexCrossReference_3_0());
                    				
                    pushFollow(FOLLOW_26);
                    ruleQualifiedName();

                    state._fsp--;


                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalHclScope.g:1137:3: ( (lv_transitionbody_5_0= ruleTransitionBody ) )
            // InternalHclScope.g:1138:4: (lv_transitionbody_5_0= ruleTransitionBody )
            {
            // InternalHclScope.g:1138:4: (lv_transitionbody_5_0= ruleTransitionBody )
            // InternalHclScope.g:1139:5: lv_transitionbody_5_0= ruleTransitionBody
            {

            					newCompositeNode(grammarAccess.getInitialTransitionAccess().getTransitionbodyTransitionBodyParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_10);
            lv_transitionbody_5_0=ruleTransitionBody();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getInitialTransitionRule());
            					}
            					set(
            						current,
            						"transitionbody",
            						lv_transitionbody_5_0,
            						"org.xtext.example.hclscope.HclScope.TransitionBody");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_6=(Token)match(input,15,FOLLOW_2); 

            			newLeafNode(otherlv_6, grammarAccess.getInitialTransitionAccess().getSemicolonKeyword_5());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInitialTransition"


    // $ANTLR start "entryRuleTransition"
    // InternalHclScope.g:1164:1: entryRuleTransition returns [EObject current=null] : iv_ruleTransition= ruleTransition EOF ;
    public final EObject entryRuleTransition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTransition = null;


        try {
            // InternalHclScope.g:1164:51: (iv_ruleTransition= ruleTransition EOF )
            // InternalHclScope.g:1165:2: iv_ruleTransition= ruleTransition EOF
            {
             newCompositeNode(grammarAccess.getTransitionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTransition=ruleTransition();

            state._fsp--;

             current =iv_ruleTransition; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTransition"


    // $ANTLR start "ruleTransition"
    // InternalHclScope.g:1171:1: ruleTransition returns [EObject current=null] : (otherlv_0= 'transition' ( ( (lv_name_1_1= RULE_ID | lv_name_1_2= RULE_STRING ) ) )? otherlv_2= ':' ( ( ruleQualifiedName ) ) otherlv_4= '->' ( ( ruleQualifiedName ) ) ( (lv_transitionbody_6_0= ruleTransitionBody ) ) otherlv_7= ';' ) ;
    public final EObject ruleTransition() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_1=null;
        Token lv_name_1_2=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_7=null;
        EObject lv_transitionbody_6_0 = null;



        	enterRule();

        try {
            // InternalHclScope.g:1177:2: ( (otherlv_0= 'transition' ( ( (lv_name_1_1= RULE_ID | lv_name_1_2= RULE_STRING ) ) )? otherlv_2= ':' ( ( ruleQualifiedName ) ) otherlv_4= '->' ( ( ruleQualifiedName ) ) ( (lv_transitionbody_6_0= ruleTransitionBody ) ) otherlv_7= ';' ) )
            // InternalHclScope.g:1178:2: (otherlv_0= 'transition' ( ( (lv_name_1_1= RULE_ID | lv_name_1_2= RULE_STRING ) ) )? otherlv_2= ':' ( ( ruleQualifiedName ) ) otherlv_4= '->' ( ( ruleQualifiedName ) ) ( (lv_transitionbody_6_0= ruleTransitionBody ) ) otherlv_7= ';' )
            {
            // InternalHclScope.g:1178:2: (otherlv_0= 'transition' ( ( (lv_name_1_1= RULE_ID | lv_name_1_2= RULE_STRING ) ) )? otherlv_2= ':' ( ( ruleQualifiedName ) ) otherlv_4= '->' ( ( ruleQualifiedName ) ) ( (lv_transitionbody_6_0= ruleTransitionBody ) ) otherlv_7= ';' )
            // InternalHclScope.g:1179:3: otherlv_0= 'transition' ( ( (lv_name_1_1= RULE_ID | lv_name_1_2= RULE_STRING ) ) )? otherlv_2= ':' ( ( ruleQualifiedName ) ) otherlv_4= '->' ( ( ruleQualifiedName ) ) ( (lv_transitionbody_6_0= ruleTransitionBody ) ) otherlv_7= ';'
            {
            otherlv_0=(Token)match(input,27,FOLLOW_27); 

            			newLeafNode(otherlv_0, grammarAccess.getTransitionAccess().getTransitionKeyword_0());
            		
            // InternalHclScope.g:1183:3: ( ( (lv_name_1_1= RULE_ID | lv_name_1_2= RULE_STRING ) ) )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( ((LA28_0>=RULE_ID && LA28_0<=RULE_STRING)) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalHclScope.g:1184:4: ( (lv_name_1_1= RULE_ID | lv_name_1_2= RULE_STRING ) )
                    {
                    // InternalHclScope.g:1184:4: ( (lv_name_1_1= RULE_ID | lv_name_1_2= RULE_STRING ) )
                    // InternalHclScope.g:1185:5: (lv_name_1_1= RULE_ID | lv_name_1_2= RULE_STRING )
                    {
                    // InternalHclScope.g:1185:5: (lv_name_1_1= RULE_ID | lv_name_1_2= RULE_STRING )
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( (LA27_0==RULE_ID) ) {
                        alt27=1;
                    }
                    else if ( (LA27_0==RULE_STRING) ) {
                        alt27=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 27, 0, input);

                        throw nvae;
                    }
                    switch (alt27) {
                        case 1 :
                            // InternalHclScope.g:1186:6: lv_name_1_1= RULE_ID
                            {
                            lv_name_1_1=(Token)match(input,RULE_ID,FOLLOW_22); 

                            						newLeafNode(lv_name_1_1, grammarAccess.getTransitionAccess().getNameIDTerminalRuleCall_1_0_0());
                            					

                            						if (current==null) {
                            							current = createModelElement(grammarAccess.getTransitionRule());
                            						}
                            						setWithLastConsumed(
                            							current,
                            							"name",
                            							lv_name_1_1,
                            							"org.eclipse.xtext.common.Terminals.ID");
                            					

                            }
                            break;
                        case 2 :
                            // InternalHclScope.g:1201:6: lv_name_1_2= RULE_STRING
                            {
                            lv_name_1_2=(Token)match(input,RULE_STRING,FOLLOW_22); 

                            						newLeafNode(lv_name_1_2, grammarAccess.getTransitionAccess().getNameSTRINGTerminalRuleCall_1_0_1());
                            					

                            						if (current==null) {
                            							current = createModelElement(grammarAccess.getTransitionRule());
                            						}
                            						setWithLastConsumed(
                            							current,
                            							"name",
                            							lv_name_1_2,
                            							"org.eclipse.xtext.common.Terminals.STRING");
                            					

                            }
                            break;

                    }


                    }


                    }
                    break;

            }

            otherlv_2=(Token)match(input,25,FOLLOW_28); 

            			newLeafNode(otherlv_2, grammarAccess.getTransitionAccess().getColonKeyword_2());
            		
            // InternalHclScope.g:1222:3: ( ( ruleQualifiedName ) )
            // InternalHclScope.g:1223:4: ( ruleQualifiedName )
            {
            // InternalHclScope.g:1223:4: ( ruleQualifiedName )
            // InternalHclScope.g:1224:5: ruleQualifiedName
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTransitionRule());
            					}
            				

            					newCompositeNode(grammarAccess.getTransitionAccess().getFromVertexCrossReference_3_0());
            				
            pushFollow(FOLLOW_24);
            ruleQualifiedName();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_4=(Token)match(input,26,FOLLOW_28); 

            			newLeafNode(otherlv_4, grammarAccess.getTransitionAccess().getHyphenMinusGreaterThanSignKeyword_4());
            		
            // InternalHclScope.g:1242:3: ( ( ruleQualifiedName ) )
            // InternalHclScope.g:1243:4: ( ruleQualifiedName )
            {
            // InternalHclScope.g:1243:4: ( ruleQualifiedName )
            // InternalHclScope.g:1244:5: ruleQualifiedName
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTransitionRule());
            					}
            				

            					newCompositeNode(grammarAccess.getTransitionAccess().getToVertexCrossReference_5_0());
            				
            pushFollow(FOLLOW_26);
            ruleQualifiedName();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalHclScope.g:1258:3: ( (lv_transitionbody_6_0= ruleTransitionBody ) )
            // InternalHclScope.g:1259:4: (lv_transitionbody_6_0= ruleTransitionBody )
            {
            // InternalHclScope.g:1259:4: (lv_transitionbody_6_0= ruleTransitionBody )
            // InternalHclScope.g:1260:5: lv_transitionbody_6_0= ruleTransitionBody
            {

            					newCompositeNode(grammarAccess.getTransitionAccess().getTransitionbodyTransitionBodyParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_10);
            lv_transitionbody_6_0=ruleTransitionBody();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTransitionRule());
            					}
            					set(
            						current,
            						"transitionbody",
            						lv_transitionbody_6_0,
            						"org.xtext.example.hclscope.HclScope.TransitionBody");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_7=(Token)match(input,15,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getTransitionAccess().getSemicolonKeyword_7());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTransition"


    // $ANTLR start "entryRuleInternalTransition"
    // InternalHclScope.g:1285:1: entryRuleInternalTransition returns [EObject current=null] : iv_ruleInternalTransition= ruleInternalTransition EOF ;
    public final EObject entryRuleInternalTransition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInternalTransition = null;


        try {
            // InternalHclScope.g:1285:59: (iv_ruleInternalTransition= ruleInternalTransition EOF )
            // InternalHclScope.g:1286:2: iv_ruleInternalTransition= ruleInternalTransition EOF
            {
             newCompositeNode(grammarAccess.getInternalTransitionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleInternalTransition=ruleInternalTransition();

            state._fsp--;

             current =iv_ruleInternalTransition; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInternalTransition"


    // $ANTLR start "ruleInternalTransition"
    // InternalHclScope.g:1292:1: ruleInternalTransition returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) )? ( (lv_transitionbody_1_0= ruleTransitionBody ) ) otherlv_2= ';' ) ;
    public final EObject ruleInternalTransition() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_2=null;
        EObject lv_transitionbody_1_0 = null;



        	enterRule();

        try {
            // InternalHclScope.g:1298:2: ( ( ( (lv_name_0_0= RULE_ID ) )? ( (lv_transitionbody_1_0= ruleTransitionBody ) ) otherlv_2= ';' ) )
            // InternalHclScope.g:1299:2: ( ( (lv_name_0_0= RULE_ID ) )? ( (lv_transitionbody_1_0= ruleTransitionBody ) ) otherlv_2= ';' )
            {
            // InternalHclScope.g:1299:2: ( ( (lv_name_0_0= RULE_ID ) )? ( (lv_transitionbody_1_0= ruleTransitionBody ) ) otherlv_2= ';' )
            // InternalHclScope.g:1300:3: ( (lv_name_0_0= RULE_ID ) )? ( (lv_transitionbody_1_0= ruleTransitionBody ) ) otherlv_2= ';'
            {
            // InternalHclScope.g:1300:3: ( (lv_name_0_0= RULE_ID ) )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==RULE_ID) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalHclScope.g:1301:4: (lv_name_0_0= RULE_ID )
                    {
                    // InternalHclScope.g:1301:4: (lv_name_0_0= RULE_ID )
                    // InternalHclScope.g:1302:5: lv_name_0_0= RULE_ID
                    {
                    lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_26); 

                    					newLeafNode(lv_name_0_0, grammarAccess.getInternalTransitionAccess().getNameIDTerminalRuleCall_0_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getInternalTransitionRule());
                    					}
                    					setWithLastConsumed(
                    						current,
                    						"name",
                    						lv_name_0_0,
                    						"org.eclipse.xtext.common.Terminals.ID");
                    				

                    }


                    }
                    break;

            }

            // InternalHclScope.g:1318:3: ( (lv_transitionbody_1_0= ruleTransitionBody ) )
            // InternalHclScope.g:1319:4: (lv_transitionbody_1_0= ruleTransitionBody )
            {
            // InternalHclScope.g:1319:4: (lv_transitionbody_1_0= ruleTransitionBody )
            // InternalHclScope.g:1320:5: lv_transitionbody_1_0= ruleTransitionBody
            {

            					newCompositeNode(grammarAccess.getInternalTransitionAccess().getTransitionbodyTransitionBodyParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_10);
            lv_transitionbody_1_0=ruleTransitionBody();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getInternalTransitionRule());
            					}
            					set(
            						current,
            						"transitionbody",
            						lv_transitionbody_1_0,
            						"org.xtext.example.hclscope.HclScope.TransitionBody");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,15,FOLLOW_2); 

            			newLeafNode(otherlv_2, grammarAccess.getInternalTransitionAccess().getSemicolonKeyword_2());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInternalTransition"


    // $ANTLR start "entryRuleHistoryTransition"
    // InternalHclScope.g:1345:1: entryRuleHistoryTransition returns [EObject current=null] : iv_ruleHistoryTransition= ruleHistoryTransition EOF ;
    public final EObject entryRuleHistoryTransition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHistoryTransition = null;


        try {
            // InternalHclScope.g:1345:58: (iv_ruleHistoryTransition= ruleHistoryTransition EOF )
            // InternalHclScope.g:1346:2: iv_ruleHistoryTransition= ruleHistoryTransition EOF
            {
             newCompositeNode(grammarAccess.getHistoryTransitionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleHistoryTransition=ruleHistoryTransition();

            state._fsp--;

             current =iv_ruleHistoryTransition; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleHistoryTransition"


    // $ANTLR start "ruleHistoryTransition"
    // InternalHclScope.g:1352:1: ruleHistoryTransition returns [EObject current=null] : (otherlv_0= 'historytransition' ( ( ( (lv_name_1_1= RULE_ID | lv_name_1_2= RULE_STRING ) ) ) otherlv_2= ':' )? ( ( ruleQualifiedName ) ) otherlv_4= '->' ( (lv_to_5_0= ruleDeepHistory ) ) ( (lv_transitionbody_6_0= ruleTransitionBody ) ) otherlv_7= ';' ) ;
    public final EObject ruleHistoryTransition() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_1=null;
        Token lv_name_1_2=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_7=null;
        EObject lv_to_5_0 = null;

        EObject lv_transitionbody_6_0 = null;



        	enterRule();

        try {
            // InternalHclScope.g:1358:2: ( (otherlv_0= 'historytransition' ( ( ( (lv_name_1_1= RULE_ID | lv_name_1_2= RULE_STRING ) ) ) otherlv_2= ':' )? ( ( ruleQualifiedName ) ) otherlv_4= '->' ( (lv_to_5_0= ruleDeepHistory ) ) ( (lv_transitionbody_6_0= ruleTransitionBody ) ) otherlv_7= ';' ) )
            // InternalHclScope.g:1359:2: (otherlv_0= 'historytransition' ( ( ( (lv_name_1_1= RULE_ID | lv_name_1_2= RULE_STRING ) ) ) otherlv_2= ':' )? ( ( ruleQualifiedName ) ) otherlv_4= '->' ( (lv_to_5_0= ruleDeepHistory ) ) ( (lv_transitionbody_6_0= ruleTransitionBody ) ) otherlv_7= ';' )
            {
            // InternalHclScope.g:1359:2: (otherlv_0= 'historytransition' ( ( ( (lv_name_1_1= RULE_ID | lv_name_1_2= RULE_STRING ) ) ) otherlv_2= ':' )? ( ( ruleQualifiedName ) ) otherlv_4= '->' ( (lv_to_5_0= ruleDeepHistory ) ) ( (lv_transitionbody_6_0= ruleTransitionBody ) ) otherlv_7= ';' )
            // InternalHclScope.g:1360:3: otherlv_0= 'historytransition' ( ( ( (lv_name_1_1= RULE_ID | lv_name_1_2= RULE_STRING ) ) ) otherlv_2= ':' )? ( ( ruleQualifiedName ) ) otherlv_4= '->' ( (lv_to_5_0= ruleDeepHistory ) ) ( (lv_transitionbody_6_0= ruleTransitionBody ) ) otherlv_7= ';'
            {
            otherlv_0=(Token)match(input,28,FOLLOW_29); 

            			newLeafNode(otherlv_0, grammarAccess.getHistoryTransitionAccess().getHistorytransitionKeyword_0());
            		
            // InternalHclScope.g:1364:3: ( ( ( (lv_name_1_1= RULE_ID | lv_name_1_2= RULE_STRING ) ) ) otherlv_2= ':' )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==RULE_ID) ) {
                int LA31_1 = input.LA(2);

                if ( (LA31_1==25) ) {
                    alt31=1;
                }
            }
            else if ( (LA31_0==RULE_STRING) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalHclScope.g:1365:4: ( ( (lv_name_1_1= RULE_ID | lv_name_1_2= RULE_STRING ) ) ) otherlv_2= ':'
                    {
                    // InternalHclScope.g:1365:4: ( ( (lv_name_1_1= RULE_ID | lv_name_1_2= RULE_STRING ) ) )
                    // InternalHclScope.g:1366:5: ( (lv_name_1_1= RULE_ID | lv_name_1_2= RULE_STRING ) )
                    {
                    // InternalHclScope.g:1366:5: ( (lv_name_1_1= RULE_ID | lv_name_1_2= RULE_STRING ) )
                    // InternalHclScope.g:1367:6: (lv_name_1_1= RULE_ID | lv_name_1_2= RULE_STRING )
                    {
                    // InternalHclScope.g:1367:6: (lv_name_1_1= RULE_ID | lv_name_1_2= RULE_STRING )
                    int alt30=2;
                    int LA30_0 = input.LA(1);

                    if ( (LA30_0==RULE_ID) ) {
                        alt30=1;
                    }
                    else if ( (LA30_0==RULE_STRING) ) {
                        alt30=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 30, 0, input);

                        throw nvae;
                    }
                    switch (alt30) {
                        case 1 :
                            // InternalHclScope.g:1368:7: lv_name_1_1= RULE_ID
                            {
                            lv_name_1_1=(Token)match(input,RULE_ID,FOLLOW_22); 

                            							newLeafNode(lv_name_1_1, grammarAccess.getHistoryTransitionAccess().getNameIDTerminalRuleCall_1_0_0_0());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getHistoryTransitionRule());
                            							}
                            							setWithLastConsumed(
                            								current,
                            								"name",
                            								lv_name_1_1,
                            								"org.eclipse.xtext.common.Terminals.ID");
                            						

                            }
                            break;
                        case 2 :
                            // InternalHclScope.g:1383:7: lv_name_1_2= RULE_STRING
                            {
                            lv_name_1_2=(Token)match(input,RULE_STRING,FOLLOW_22); 

                            							newLeafNode(lv_name_1_2, grammarAccess.getHistoryTransitionAccess().getNameSTRINGTerminalRuleCall_1_0_0_1());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getHistoryTransitionRule());
                            							}
                            							setWithLastConsumed(
                            								current,
                            								"name",
                            								lv_name_1_2,
                            								"org.eclipse.xtext.common.Terminals.STRING");
                            						

                            }
                            break;

                    }


                    }


                    }

                    otherlv_2=(Token)match(input,25,FOLLOW_28); 

                    				newLeafNode(otherlv_2, grammarAccess.getHistoryTransitionAccess().getColonKeyword_1_1());
                    			

                    }
                    break;

            }

            // InternalHclScope.g:1405:3: ( ( ruleQualifiedName ) )
            // InternalHclScope.g:1406:4: ( ruleQualifiedName )
            {
            // InternalHclScope.g:1406:4: ( ruleQualifiedName )
            // InternalHclScope.g:1407:5: ruleQualifiedName
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getHistoryTransitionRule());
            					}
            				

            					newCompositeNode(grammarAccess.getHistoryTransitionAccess().getFromVertexCrossReference_2_0());
            				
            pushFollow(FOLLOW_24);
            ruleQualifiedName();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_4=(Token)match(input,26,FOLLOW_30); 

            			newLeafNode(otherlv_4, grammarAccess.getHistoryTransitionAccess().getHyphenMinusGreaterThanSignKeyword_3());
            		
            // InternalHclScope.g:1425:3: ( (lv_to_5_0= ruleDeepHistory ) )
            // InternalHclScope.g:1426:4: (lv_to_5_0= ruleDeepHistory )
            {
            // InternalHclScope.g:1426:4: (lv_to_5_0= ruleDeepHistory )
            // InternalHclScope.g:1427:5: lv_to_5_0= ruleDeepHistory
            {

            					newCompositeNode(grammarAccess.getHistoryTransitionAccess().getToDeepHistoryParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_26);
            lv_to_5_0=ruleDeepHistory();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getHistoryTransitionRule());
            					}
            					set(
            						current,
            						"to",
            						lv_to_5_0,
            						"org.xtext.example.hclscope.HclScope.DeepHistory");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalHclScope.g:1444:3: ( (lv_transitionbody_6_0= ruleTransitionBody ) )
            // InternalHclScope.g:1445:4: (lv_transitionbody_6_0= ruleTransitionBody )
            {
            // InternalHclScope.g:1445:4: (lv_transitionbody_6_0= ruleTransitionBody )
            // InternalHclScope.g:1446:5: lv_transitionbody_6_0= ruleTransitionBody
            {

            					newCompositeNode(grammarAccess.getHistoryTransitionAccess().getTransitionbodyTransitionBodyParserRuleCall_5_0());
            				
            pushFollow(FOLLOW_10);
            lv_transitionbody_6_0=ruleTransitionBody();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getHistoryTransitionRule());
            					}
            					set(
            						current,
            						"transitionbody",
            						lv_transitionbody_6_0,
            						"org.xtext.example.hclscope.HclScope.TransitionBody");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_7=(Token)match(input,15,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getHistoryTransitionAccess().getSemicolonKeyword_6());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleHistoryTransition"


    // $ANTLR start "entryRuleTransitionBody"
    // InternalHclScope.g:1471:1: entryRuleTransitionBody returns [EObject current=null] : iv_ruleTransitionBody= ruleTransitionBody EOF ;
    public final EObject entryRuleTransitionBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTransitionBody = null;


        try {
            // InternalHclScope.g:1471:55: (iv_ruleTransitionBody= ruleTransitionBody EOF )
            // InternalHclScope.g:1472:2: iv_ruleTransitionBody= ruleTransitionBody EOF
            {
             newCompositeNode(grammarAccess.getTransitionBodyRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTransitionBody=ruleTransitionBody();

            state._fsp--;

             current =iv_ruleTransitionBody; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTransitionBody"


    // $ANTLR start "ruleTransitionBody"
    // InternalHclScope.g:1478:1: ruleTransitionBody returns [EObject current=null] : ( () (otherlv_1= 'on' ( ( (lv_methodparameter_2_0= ruleMethodParameterTrigger ) ) | ( (lv_portevent_3_0= rulePortEventTrigger ) ) | ( (lv_trigger_4_0= ruleTrigger ) ) | otherlv_5= '*' ) )? ( (lv_transitionguard_6_0= ruleTransitionGuard ) )? ( (lv_transitionoperation_7_0= ruleTransitionOperation ) )? (otherlv_8= ',' ( ( (lv_methodparameter_9_0= ruleMethodParameterTrigger ) ) | ( (lv_portevent_10_0= rulePortEventTrigger ) ) | ( (lv_trigger_11_0= ruleTrigger ) ) )? ( (lv_transitionguard_12_0= ruleTransitionGuard ) )? ( (lv_transitionoperation_13_0= ruleTransitionOperation ) )? )* ) ;
    public final EObject ruleTransitionBody() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_5=null;
        Token otherlv_8=null;
        EObject lv_methodparameter_2_0 = null;

        EObject lv_portevent_3_0 = null;

        EObject lv_trigger_4_0 = null;

        EObject lv_transitionguard_6_0 = null;

        EObject lv_transitionoperation_7_0 = null;

        EObject lv_methodparameter_9_0 = null;

        EObject lv_portevent_10_0 = null;

        EObject lv_trigger_11_0 = null;

        EObject lv_transitionguard_12_0 = null;

        EObject lv_transitionoperation_13_0 = null;



        	enterRule();

        try {
            // InternalHclScope.g:1484:2: ( ( () (otherlv_1= 'on' ( ( (lv_methodparameter_2_0= ruleMethodParameterTrigger ) ) | ( (lv_portevent_3_0= rulePortEventTrigger ) ) | ( (lv_trigger_4_0= ruleTrigger ) ) | otherlv_5= '*' ) )? ( (lv_transitionguard_6_0= ruleTransitionGuard ) )? ( (lv_transitionoperation_7_0= ruleTransitionOperation ) )? (otherlv_8= ',' ( ( (lv_methodparameter_9_0= ruleMethodParameterTrigger ) ) | ( (lv_portevent_10_0= rulePortEventTrigger ) ) | ( (lv_trigger_11_0= ruleTrigger ) ) )? ( (lv_transitionguard_12_0= ruleTransitionGuard ) )? ( (lv_transitionoperation_13_0= ruleTransitionOperation ) )? )* ) )
            // InternalHclScope.g:1485:2: ( () (otherlv_1= 'on' ( ( (lv_methodparameter_2_0= ruleMethodParameterTrigger ) ) | ( (lv_portevent_3_0= rulePortEventTrigger ) ) | ( (lv_trigger_4_0= ruleTrigger ) ) | otherlv_5= '*' ) )? ( (lv_transitionguard_6_0= ruleTransitionGuard ) )? ( (lv_transitionoperation_7_0= ruleTransitionOperation ) )? (otherlv_8= ',' ( ( (lv_methodparameter_9_0= ruleMethodParameterTrigger ) ) | ( (lv_portevent_10_0= rulePortEventTrigger ) ) | ( (lv_trigger_11_0= ruleTrigger ) ) )? ( (lv_transitionguard_12_0= ruleTransitionGuard ) )? ( (lv_transitionoperation_13_0= ruleTransitionOperation ) )? )* )
            {
            // InternalHclScope.g:1485:2: ( () (otherlv_1= 'on' ( ( (lv_methodparameter_2_0= ruleMethodParameterTrigger ) ) | ( (lv_portevent_3_0= rulePortEventTrigger ) ) | ( (lv_trigger_4_0= ruleTrigger ) ) | otherlv_5= '*' ) )? ( (lv_transitionguard_6_0= ruleTransitionGuard ) )? ( (lv_transitionoperation_7_0= ruleTransitionOperation ) )? (otherlv_8= ',' ( ( (lv_methodparameter_9_0= ruleMethodParameterTrigger ) ) | ( (lv_portevent_10_0= rulePortEventTrigger ) ) | ( (lv_trigger_11_0= ruleTrigger ) ) )? ( (lv_transitionguard_12_0= ruleTransitionGuard ) )? ( (lv_transitionoperation_13_0= ruleTransitionOperation ) )? )* )
            // InternalHclScope.g:1486:3: () (otherlv_1= 'on' ( ( (lv_methodparameter_2_0= ruleMethodParameterTrigger ) ) | ( (lv_portevent_3_0= rulePortEventTrigger ) ) | ( (lv_trigger_4_0= ruleTrigger ) ) | otherlv_5= '*' ) )? ( (lv_transitionguard_6_0= ruleTransitionGuard ) )? ( (lv_transitionoperation_7_0= ruleTransitionOperation ) )? (otherlv_8= ',' ( ( (lv_methodparameter_9_0= ruleMethodParameterTrigger ) ) | ( (lv_portevent_10_0= rulePortEventTrigger ) ) | ( (lv_trigger_11_0= ruleTrigger ) ) )? ( (lv_transitionguard_12_0= ruleTransitionGuard ) )? ( (lv_transitionoperation_13_0= ruleTransitionOperation ) )? )*
            {
            // InternalHclScope.g:1486:3: ()
            // InternalHclScope.g:1487:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getTransitionBodyAccess().getTransitionBodyAction_0(),
            					current);
            			

            }

            // InternalHclScope.g:1493:3: (otherlv_1= 'on' ( ( (lv_methodparameter_2_0= ruleMethodParameterTrigger ) ) | ( (lv_portevent_3_0= rulePortEventTrigger ) ) | ( (lv_trigger_4_0= ruleTrigger ) ) | otherlv_5= '*' ) )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==29) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalHclScope.g:1494:4: otherlv_1= 'on' ( ( (lv_methodparameter_2_0= ruleMethodParameterTrigger ) ) | ( (lv_portevent_3_0= rulePortEventTrigger ) ) | ( (lv_trigger_4_0= ruleTrigger ) ) | otherlv_5= '*' )
                    {
                    otherlv_1=(Token)match(input,29,FOLLOW_31); 

                    				newLeafNode(otherlv_1, grammarAccess.getTransitionBodyAccess().getOnKeyword_1_0());
                    			
                    // InternalHclScope.g:1498:4: ( ( (lv_methodparameter_2_0= ruleMethodParameterTrigger ) ) | ( (lv_portevent_3_0= rulePortEventTrigger ) ) | ( (lv_trigger_4_0= ruleTrigger ) ) | otherlv_5= '*' )
                    int alt32=4;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==RULE_ID) ) {
                        switch ( input.LA(2) ) {
                        case EOF:
                        case 12:
                        case 14:
                        case 15:
                        case 31:
                            {
                            alt32=3;
                            }
                            break;
                        case 36:
                            {
                            alt32=2;
                            }
                            break;
                        case 34:
                            {
                            alt32=1;
                            }
                            break;
                        default:
                            NoViableAltException nvae =
                                new NoViableAltException("", 32, 1, input);

                            throw nvae;
                        }

                    }
                    else if ( (LA32_0==30) ) {
                        alt32=4;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 32, 0, input);

                        throw nvae;
                    }
                    switch (alt32) {
                        case 1 :
                            // InternalHclScope.g:1499:5: ( (lv_methodparameter_2_0= ruleMethodParameterTrigger ) )
                            {
                            // InternalHclScope.g:1499:5: ( (lv_methodparameter_2_0= ruleMethodParameterTrigger ) )
                            // InternalHclScope.g:1500:6: (lv_methodparameter_2_0= ruleMethodParameterTrigger )
                            {
                            // InternalHclScope.g:1500:6: (lv_methodparameter_2_0= ruleMethodParameterTrigger )
                            // InternalHclScope.g:1501:7: lv_methodparameter_2_0= ruleMethodParameterTrigger
                            {

                            							newCompositeNode(grammarAccess.getTransitionBodyAccess().getMethodparameterMethodParameterTriggerParserRuleCall_1_1_0_0());
                            						
                            pushFollow(FOLLOW_32);
                            lv_methodparameter_2_0=ruleMethodParameterTrigger();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getTransitionBodyRule());
                            							}
                            							add(
                            								current,
                            								"methodparameter",
                            								lv_methodparameter_2_0,
                            								"org.xtext.example.hclscope.HclScope.MethodParameterTrigger");
                            							afterParserOrEnumRuleCall();
                            						

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalHclScope.g:1519:5: ( (lv_portevent_3_0= rulePortEventTrigger ) )
                            {
                            // InternalHclScope.g:1519:5: ( (lv_portevent_3_0= rulePortEventTrigger ) )
                            // InternalHclScope.g:1520:6: (lv_portevent_3_0= rulePortEventTrigger )
                            {
                            // InternalHclScope.g:1520:6: (lv_portevent_3_0= rulePortEventTrigger )
                            // InternalHclScope.g:1521:7: lv_portevent_3_0= rulePortEventTrigger
                            {

                            							newCompositeNode(grammarAccess.getTransitionBodyAccess().getPorteventPortEventTriggerParserRuleCall_1_1_1_0());
                            						
                            pushFollow(FOLLOW_32);
                            lv_portevent_3_0=rulePortEventTrigger();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getTransitionBodyRule());
                            							}
                            							add(
                            								current,
                            								"portevent",
                            								lv_portevent_3_0,
                            								"org.xtext.example.hclscope.HclScope.PortEventTrigger");
                            							afterParserOrEnumRuleCall();
                            						

                            }


                            }


                            }
                            break;
                        case 3 :
                            // InternalHclScope.g:1539:5: ( (lv_trigger_4_0= ruleTrigger ) )
                            {
                            // InternalHclScope.g:1539:5: ( (lv_trigger_4_0= ruleTrigger ) )
                            // InternalHclScope.g:1540:6: (lv_trigger_4_0= ruleTrigger )
                            {
                            // InternalHclScope.g:1540:6: (lv_trigger_4_0= ruleTrigger )
                            // InternalHclScope.g:1541:7: lv_trigger_4_0= ruleTrigger
                            {

                            							newCompositeNode(grammarAccess.getTransitionBodyAccess().getTriggerTriggerParserRuleCall_1_1_2_0());
                            						
                            pushFollow(FOLLOW_32);
                            lv_trigger_4_0=ruleTrigger();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getTransitionBodyRule());
                            							}
                            							add(
                            								current,
                            								"trigger",
                            								lv_trigger_4_0,
                            								"org.xtext.example.hclscope.HclScope.Trigger");
                            							afterParserOrEnumRuleCall();
                            						

                            }


                            }


                            }
                            break;
                        case 4 :
                            // InternalHclScope.g:1559:5: otherlv_5= '*'
                            {
                            otherlv_5=(Token)match(input,30,FOLLOW_32); 

                            					newLeafNode(otherlv_5, grammarAccess.getTransitionBodyAccess().getAsteriskKeyword_1_1_3());
                            				

                            }
                            break;

                    }


                    }
                    break;

            }

            // InternalHclScope.g:1565:3: ( (lv_transitionguard_6_0= ruleTransitionGuard ) )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==31) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalHclScope.g:1566:4: (lv_transitionguard_6_0= ruleTransitionGuard )
                    {
                    // InternalHclScope.g:1566:4: (lv_transitionguard_6_0= ruleTransitionGuard )
                    // InternalHclScope.g:1567:5: lv_transitionguard_6_0= ruleTransitionGuard
                    {

                    					newCompositeNode(grammarAccess.getTransitionBodyAccess().getTransitionguardTransitionGuardParserRuleCall_2_0());
                    				
                    pushFollow(FOLLOW_33);
                    lv_transitionguard_6_0=ruleTransitionGuard();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getTransitionBodyRule());
                    					}
                    					set(
                    						current,
                    						"transitionguard",
                    						lv_transitionguard_6_0,
                    						"org.xtext.example.hclscope.HclScope.TransitionGuard");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalHclScope.g:1584:3: ( (lv_transitionoperation_7_0= ruleTransitionOperation ) )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==12) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalHclScope.g:1585:4: (lv_transitionoperation_7_0= ruleTransitionOperation )
                    {
                    // InternalHclScope.g:1585:4: (lv_transitionoperation_7_0= ruleTransitionOperation )
                    // InternalHclScope.g:1586:5: lv_transitionoperation_7_0= ruleTransitionOperation
                    {

                    					newCompositeNode(grammarAccess.getTransitionBodyAccess().getTransitionoperationTransitionOperationParserRuleCall_3_0());
                    				
                    pushFollow(FOLLOW_34);
                    lv_transitionoperation_7_0=ruleTransitionOperation();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getTransitionBodyRule());
                    					}
                    					set(
                    						current,
                    						"transitionoperation",
                    						lv_transitionoperation_7_0,
                    						"org.xtext.example.hclscope.HclScope.TransitionOperation");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalHclScope.g:1603:3: (otherlv_8= ',' ( ( (lv_methodparameter_9_0= ruleMethodParameterTrigger ) ) | ( (lv_portevent_10_0= rulePortEventTrigger ) ) | ( (lv_trigger_11_0= ruleTrigger ) ) )? ( (lv_transitionguard_12_0= ruleTransitionGuard ) )? ( (lv_transitionoperation_13_0= ruleTransitionOperation ) )? )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==14) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalHclScope.g:1604:4: otherlv_8= ',' ( ( (lv_methodparameter_9_0= ruleMethodParameterTrigger ) ) | ( (lv_portevent_10_0= rulePortEventTrigger ) ) | ( (lv_trigger_11_0= ruleTrigger ) ) )? ( (lv_transitionguard_12_0= ruleTransitionGuard ) )? ( (lv_transitionoperation_13_0= ruleTransitionOperation ) )?
            	    {
            	    otherlv_8=(Token)match(input,14,FOLLOW_35); 

            	    				newLeafNode(otherlv_8, grammarAccess.getTransitionBodyAccess().getCommaKeyword_4_0());
            	    			
            	    // InternalHclScope.g:1608:4: ( ( (lv_methodparameter_9_0= ruleMethodParameterTrigger ) ) | ( (lv_portevent_10_0= rulePortEventTrigger ) ) | ( (lv_trigger_11_0= ruleTrigger ) ) )?
            	    int alt36=4;
            	    int LA36_0 = input.LA(1);

            	    if ( (LA36_0==RULE_ID) ) {
            	        switch ( input.LA(2) ) {
            	            case 36:
            	                {
            	                alt36=2;
            	                }
            	                break;
            	            case 34:
            	                {
            	                alt36=1;
            	                }
            	                break;
            	            case EOF:
            	            case 12:
            	            case 14:
            	            case 15:
            	            case 31:
            	                {
            	                alt36=3;
            	                }
            	                break;
            	        }

            	    }
            	    switch (alt36) {
            	        case 1 :
            	            // InternalHclScope.g:1609:5: ( (lv_methodparameter_9_0= ruleMethodParameterTrigger ) )
            	            {
            	            // InternalHclScope.g:1609:5: ( (lv_methodparameter_9_0= ruleMethodParameterTrigger ) )
            	            // InternalHclScope.g:1610:6: (lv_methodparameter_9_0= ruleMethodParameterTrigger )
            	            {
            	            // InternalHclScope.g:1610:6: (lv_methodparameter_9_0= ruleMethodParameterTrigger )
            	            // InternalHclScope.g:1611:7: lv_methodparameter_9_0= ruleMethodParameterTrigger
            	            {

            	            							newCompositeNode(grammarAccess.getTransitionBodyAccess().getMethodparameterMethodParameterTriggerParserRuleCall_4_1_0_0());
            	            						
            	            pushFollow(FOLLOW_32);
            	            lv_methodparameter_9_0=ruleMethodParameterTrigger();

            	            state._fsp--;


            	            							if (current==null) {
            	            								current = createModelElementForParent(grammarAccess.getTransitionBodyRule());
            	            							}
            	            							add(
            	            								current,
            	            								"methodparameter",
            	            								lv_methodparameter_9_0,
            	            								"org.xtext.example.hclscope.HclScope.MethodParameterTrigger");
            	            							afterParserOrEnumRuleCall();
            	            						

            	            }


            	            }


            	            }
            	            break;
            	        case 2 :
            	            // InternalHclScope.g:1629:5: ( (lv_portevent_10_0= rulePortEventTrigger ) )
            	            {
            	            // InternalHclScope.g:1629:5: ( (lv_portevent_10_0= rulePortEventTrigger ) )
            	            // InternalHclScope.g:1630:6: (lv_portevent_10_0= rulePortEventTrigger )
            	            {
            	            // InternalHclScope.g:1630:6: (lv_portevent_10_0= rulePortEventTrigger )
            	            // InternalHclScope.g:1631:7: lv_portevent_10_0= rulePortEventTrigger
            	            {

            	            							newCompositeNode(grammarAccess.getTransitionBodyAccess().getPorteventPortEventTriggerParserRuleCall_4_1_1_0());
            	            						
            	            pushFollow(FOLLOW_32);
            	            lv_portevent_10_0=rulePortEventTrigger();

            	            state._fsp--;


            	            							if (current==null) {
            	            								current = createModelElementForParent(grammarAccess.getTransitionBodyRule());
            	            							}
            	            							add(
            	            								current,
            	            								"portevent",
            	            								lv_portevent_10_0,
            	            								"org.xtext.example.hclscope.HclScope.PortEventTrigger");
            	            							afterParserOrEnumRuleCall();
            	            						

            	            }


            	            }


            	            }
            	            break;
            	        case 3 :
            	            // InternalHclScope.g:1649:5: ( (lv_trigger_11_0= ruleTrigger ) )
            	            {
            	            // InternalHclScope.g:1649:5: ( (lv_trigger_11_0= ruleTrigger ) )
            	            // InternalHclScope.g:1650:6: (lv_trigger_11_0= ruleTrigger )
            	            {
            	            // InternalHclScope.g:1650:6: (lv_trigger_11_0= ruleTrigger )
            	            // InternalHclScope.g:1651:7: lv_trigger_11_0= ruleTrigger
            	            {

            	            							newCompositeNode(grammarAccess.getTransitionBodyAccess().getTriggerTriggerParserRuleCall_4_1_2_0());
            	            						
            	            pushFollow(FOLLOW_32);
            	            lv_trigger_11_0=ruleTrigger();

            	            state._fsp--;


            	            							if (current==null) {
            	            								current = createModelElementForParent(grammarAccess.getTransitionBodyRule());
            	            							}
            	            							add(
            	            								current,
            	            								"trigger",
            	            								lv_trigger_11_0,
            	            								"org.xtext.example.hclscope.HclScope.Trigger");
            	            							afterParserOrEnumRuleCall();
            	            						

            	            }


            	            }


            	            }
            	            break;

            	    }

            	    // InternalHclScope.g:1669:4: ( (lv_transitionguard_12_0= ruleTransitionGuard ) )?
            	    int alt37=2;
            	    int LA37_0 = input.LA(1);

            	    if ( (LA37_0==31) ) {
            	        alt37=1;
            	    }
            	    switch (alt37) {
            	        case 1 :
            	            // InternalHclScope.g:1670:5: (lv_transitionguard_12_0= ruleTransitionGuard )
            	            {
            	            // InternalHclScope.g:1670:5: (lv_transitionguard_12_0= ruleTransitionGuard )
            	            // InternalHclScope.g:1671:6: lv_transitionguard_12_0= ruleTransitionGuard
            	            {

            	            						newCompositeNode(grammarAccess.getTransitionBodyAccess().getTransitionguardTransitionGuardParserRuleCall_4_2_0());
            	            					
            	            pushFollow(FOLLOW_33);
            	            lv_transitionguard_12_0=ruleTransitionGuard();

            	            state._fsp--;


            	            						if (current==null) {
            	            							current = createModelElementForParent(grammarAccess.getTransitionBodyRule());
            	            						}
            	            						set(
            	            							current,
            	            							"transitionguard",
            	            							lv_transitionguard_12_0,
            	            							"org.xtext.example.hclscope.HclScope.TransitionGuard");
            	            						afterParserOrEnumRuleCall();
            	            					

            	            }


            	            }
            	            break;

            	    }

            	    // InternalHclScope.g:1688:4: ( (lv_transitionoperation_13_0= ruleTransitionOperation ) )?
            	    int alt38=2;
            	    int LA38_0 = input.LA(1);

            	    if ( (LA38_0==12) ) {
            	        alt38=1;
            	    }
            	    switch (alt38) {
            	        case 1 :
            	            // InternalHclScope.g:1689:5: (lv_transitionoperation_13_0= ruleTransitionOperation )
            	            {
            	            // InternalHclScope.g:1689:5: (lv_transitionoperation_13_0= ruleTransitionOperation )
            	            // InternalHclScope.g:1690:6: lv_transitionoperation_13_0= ruleTransitionOperation
            	            {

            	            						newCompositeNode(grammarAccess.getTransitionBodyAccess().getTransitionoperationTransitionOperationParserRuleCall_4_3_0());
            	            					
            	            pushFollow(FOLLOW_34);
            	            lv_transitionoperation_13_0=ruleTransitionOperation();

            	            state._fsp--;


            	            						if (current==null) {
            	            							current = createModelElementForParent(grammarAccess.getTransitionBodyRule());
            	            						}
            	            						set(
            	            							current,
            	            							"transitionoperation",
            	            							lv_transitionoperation_13_0,
            	            							"org.xtext.example.hclscope.HclScope.TransitionOperation");
            	            						afterParserOrEnumRuleCall();
            	            					

            	            }


            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop39;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTransitionBody"


    // $ANTLR start "entryRuleTransitionGuard"
    // InternalHclScope.g:1712:1: entryRuleTransitionGuard returns [EObject current=null] : iv_ruleTransitionGuard= ruleTransitionGuard EOF ;
    public final EObject entryRuleTransitionGuard() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTransitionGuard = null;


        try {
            // InternalHclScope.g:1712:56: (iv_ruleTransitionGuard= ruleTransitionGuard EOF )
            // InternalHclScope.g:1713:2: iv_ruleTransitionGuard= ruleTransitionGuard EOF
            {
             newCompositeNode(grammarAccess.getTransitionGuardRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTransitionGuard=ruleTransitionGuard();

            state._fsp--;

             current =iv_ruleTransitionGuard; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTransitionGuard"


    // $ANTLR start "ruleTransitionGuard"
    // InternalHclScope.g:1719:1: ruleTransitionGuard returns [EObject current=null] : (otherlv_0= 'when' otherlv_1= '[' ( (lv_name_2_0= RULE_STRING ) ) otherlv_3= ']' ) ;
    public final EObject ruleTransitionGuard() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalHclScope.g:1725:2: ( (otherlv_0= 'when' otherlv_1= '[' ( (lv_name_2_0= RULE_STRING ) ) otherlv_3= ']' ) )
            // InternalHclScope.g:1726:2: (otherlv_0= 'when' otherlv_1= '[' ( (lv_name_2_0= RULE_STRING ) ) otherlv_3= ']' )
            {
            // InternalHclScope.g:1726:2: (otherlv_0= 'when' otherlv_1= '[' ( (lv_name_2_0= RULE_STRING ) ) otherlv_3= ']' )
            // InternalHclScope.g:1727:3: otherlv_0= 'when' otherlv_1= '[' ( (lv_name_2_0= RULE_STRING ) ) otherlv_3= ']'
            {
            otherlv_0=(Token)match(input,31,FOLLOW_36); 

            			newLeafNode(otherlv_0, grammarAccess.getTransitionGuardAccess().getWhenKeyword_0());
            		
            otherlv_1=(Token)match(input,32,FOLLOW_13); 

            			newLeafNode(otherlv_1, grammarAccess.getTransitionGuardAccess().getLeftSquareBracketKeyword_1());
            		
            // InternalHclScope.g:1735:3: ( (lv_name_2_0= RULE_STRING ) )
            // InternalHclScope.g:1736:4: (lv_name_2_0= RULE_STRING )
            {
            // InternalHclScope.g:1736:4: (lv_name_2_0= RULE_STRING )
            // InternalHclScope.g:1737:5: lv_name_2_0= RULE_STRING
            {
            lv_name_2_0=(Token)match(input,RULE_STRING,FOLLOW_37); 

            					newLeafNode(lv_name_2_0, grammarAccess.getTransitionGuardAccess().getNameSTRINGTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTransitionGuardRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_2_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_3=(Token)match(input,33,FOLLOW_2); 

            			newLeafNode(otherlv_3, grammarAccess.getTransitionGuardAccess().getRightSquareBracketKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTransitionGuard"


    // $ANTLR start "entryRuleTransitionOperation"
    // InternalHclScope.g:1761:1: entryRuleTransitionOperation returns [EObject current=null] : iv_ruleTransitionOperation= ruleTransitionOperation EOF ;
    public final EObject entryRuleTransitionOperation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTransitionOperation = null;


        try {
            // InternalHclScope.g:1761:60: (iv_ruleTransitionOperation= ruleTransitionOperation EOF )
            // InternalHclScope.g:1762:2: iv_ruleTransitionOperation= ruleTransitionOperation EOF
            {
             newCompositeNode(grammarAccess.getTransitionOperationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTransitionOperation=ruleTransitionOperation();

            state._fsp--;

             current =iv_ruleTransitionOperation; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTransitionOperation"


    // $ANTLR start "ruleTransitionOperation"
    // InternalHclScope.g:1768:1: ruleTransitionOperation returns [EObject current=null] : (otherlv_0= '{' ( (lv_name_1_0= RULE_STRING ) ) otherlv_2= '}' ) ;
    public final EObject ruleTransitionOperation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalHclScope.g:1774:2: ( (otherlv_0= '{' ( (lv_name_1_0= RULE_STRING ) ) otherlv_2= '}' ) )
            // InternalHclScope.g:1775:2: (otherlv_0= '{' ( (lv_name_1_0= RULE_STRING ) ) otherlv_2= '}' )
            {
            // InternalHclScope.g:1775:2: (otherlv_0= '{' ( (lv_name_1_0= RULE_STRING ) ) otherlv_2= '}' )
            // InternalHclScope.g:1776:3: otherlv_0= '{' ( (lv_name_1_0= RULE_STRING ) ) otherlv_2= '}'
            {
            otherlv_0=(Token)match(input,12,FOLLOW_13); 

            			newLeafNode(otherlv_0, grammarAccess.getTransitionOperationAccess().getLeftCurlyBracketKeyword_0());
            		
            // InternalHclScope.g:1780:3: ( (lv_name_1_0= RULE_STRING ) )
            // InternalHclScope.g:1781:4: (lv_name_1_0= RULE_STRING )
            {
            // InternalHclScope.g:1781:4: (lv_name_1_0= RULE_STRING )
            // InternalHclScope.g:1782:5: lv_name_1_0= RULE_STRING
            {
            lv_name_1_0=(Token)match(input,RULE_STRING,FOLLOW_14); 

            					newLeafNode(lv_name_1_0, grammarAccess.getTransitionOperationAccess().getNameSTRINGTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTransitionOperationRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_2=(Token)match(input,18,FOLLOW_2); 

            			newLeafNode(otherlv_2, grammarAccess.getTransitionOperationAccess().getRightCurlyBracketKeyword_2());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTransitionOperation"


    // $ANTLR start "entryRuleTrigger"
    // InternalHclScope.g:1806:1: entryRuleTrigger returns [EObject current=null] : iv_ruleTrigger= ruleTrigger EOF ;
    public final EObject entryRuleTrigger() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTrigger = null;


        try {
            // InternalHclScope.g:1806:48: (iv_ruleTrigger= ruleTrigger EOF )
            // InternalHclScope.g:1807:2: iv_ruleTrigger= ruleTrigger EOF
            {
             newCompositeNode(grammarAccess.getTriggerRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTrigger=ruleTrigger();

            state._fsp--;

             current =iv_ruleTrigger; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTrigger"


    // $ANTLR start "ruleTrigger"
    // InternalHclScope.g:1813:1: ruleTrigger returns [EObject current=null] : ( (lv_name_0_0= RULE_ID ) ) ;
    public final EObject ruleTrigger() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;


        	enterRule();

        try {
            // InternalHclScope.g:1819:2: ( ( (lv_name_0_0= RULE_ID ) ) )
            // InternalHclScope.g:1820:2: ( (lv_name_0_0= RULE_ID ) )
            {
            // InternalHclScope.g:1820:2: ( (lv_name_0_0= RULE_ID ) )
            // InternalHclScope.g:1821:3: (lv_name_0_0= RULE_ID )
            {
            // InternalHclScope.g:1821:3: (lv_name_0_0= RULE_ID )
            // InternalHclScope.g:1822:4: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            				newLeafNode(lv_name_0_0, grammarAccess.getTriggerAccess().getNameIDTerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getTriggerRule());
            				}
            				setWithLastConsumed(
            					current,
            					"name",
            					lv_name_0_0,
            					"org.eclipse.xtext.common.Terminals.ID");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTrigger"


    // $ANTLR start "entryRuleMethod"
    // InternalHclScope.g:1841:1: entryRuleMethod returns [EObject current=null] : iv_ruleMethod= ruleMethod EOF ;
    public final EObject entryRuleMethod() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMethod = null;


        try {
            // InternalHclScope.g:1841:47: (iv_ruleMethod= ruleMethod EOF )
            // InternalHclScope.g:1842:2: iv_ruleMethod= ruleMethod EOF
            {
             newCompositeNode(grammarAccess.getMethodRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMethod=ruleMethod();

            state._fsp--;

             current =iv_ruleMethod; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMethod"


    // $ANTLR start "ruleMethod"
    // InternalHclScope.g:1848:1: ruleMethod returns [EObject current=null] : ( (lv_name_0_0= RULE_ID ) ) ;
    public final EObject ruleMethod() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;


        	enterRule();

        try {
            // InternalHclScope.g:1854:2: ( ( (lv_name_0_0= RULE_ID ) ) )
            // InternalHclScope.g:1855:2: ( (lv_name_0_0= RULE_ID ) )
            {
            // InternalHclScope.g:1855:2: ( (lv_name_0_0= RULE_ID ) )
            // InternalHclScope.g:1856:3: (lv_name_0_0= RULE_ID )
            {
            // InternalHclScope.g:1856:3: (lv_name_0_0= RULE_ID )
            // InternalHclScope.g:1857:4: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            				newLeafNode(lv_name_0_0, grammarAccess.getMethodAccess().getNameIDTerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getMethodRule());
            				}
            				setWithLastConsumed(
            					current,
            					"name",
            					lv_name_0_0,
            					"org.eclipse.xtext.common.Terminals.ID");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMethod"


    // $ANTLR start "entryRuleParameter"
    // InternalHclScope.g:1876:1: entryRuleParameter returns [EObject current=null] : iv_ruleParameter= ruleParameter EOF ;
    public final EObject entryRuleParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameter = null;


        try {
            // InternalHclScope.g:1876:50: (iv_ruleParameter= ruleParameter EOF )
            // InternalHclScope.g:1877:2: iv_ruleParameter= ruleParameter EOF
            {
             newCompositeNode(grammarAccess.getParameterRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleParameter=ruleParameter();

            state._fsp--;

             current =iv_ruleParameter; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleParameter"


    // $ANTLR start "ruleParameter"
    // InternalHclScope.g:1883:1: ruleParameter returns [EObject current=null] : ( (lv_name_0_0= RULE_ID ) ) ;
    public final EObject ruleParameter() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;


        	enterRule();

        try {
            // InternalHclScope.g:1889:2: ( ( (lv_name_0_0= RULE_ID ) ) )
            // InternalHclScope.g:1890:2: ( (lv_name_0_0= RULE_ID ) )
            {
            // InternalHclScope.g:1890:2: ( (lv_name_0_0= RULE_ID ) )
            // InternalHclScope.g:1891:3: (lv_name_0_0= RULE_ID )
            {
            // InternalHclScope.g:1891:3: (lv_name_0_0= RULE_ID )
            // InternalHclScope.g:1892:4: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            				newLeafNode(lv_name_0_0, grammarAccess.getParameterAccess().getNameIDTerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getParameterRule());
            				}
            				setWithLastConsumed(
            					current,
            					"name",
            					lv_name_0_0,
            					"org.eclipse.xtext.common.Terminals.ID");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleParameter"


    // $ANTLR start "entryRuleMethodParameterTrigger"
    // InternalHclScope.g:1911:1: entryRuleMethodParameterTrigger returns [EObject current=null] : iv_ruleMethodParameterTrigger= ruleMethodParameterTrigger EOF ;
    public final EObject entryRuleMethodParameterTrigger() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMethodParameterTrigger = null;


        try {
            // InternalHclScope.g:1911:63: (iv_ruleMethodParameterTrigger= ruleMethodParameterTrigger EOF )
            // InternalHclScope.g:1912:2: iv_ruleMethodParameterTrigger= ruleMethodParameterTrigger EOF
            {
             newCompositeNode(grammarAccess.getMethodParameterTriggerRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMethodParameterTrigger=ruleMethodParameterTrigger();

            state._fsp--;

             current =iv_ruleMethodParameterTrigger; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMethodParameterTrigger"


    // $ANTLR start "ruleMethodParameterTrigger"
    // InternalHclScope.g:1918:1: ruleMethodParameterTrigger returns [EObject current=null] : ( ( (lv_method_0_0= ruleMethod ) ) otherlv_1= '(' ( (lv_parameter_2_0= ruleParameter ) ) otherlv_3= ')' ) ;
    public final EObject ruleMethodParameterTrigger() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_method_0_0 = null;

        EObject lv_parameter_2_0 = null;



        	enterRule();

        try {
            // InternalHclScope.g:1924:2: ( ( ( (lv_method_0_0= ruleMethod ) ) otherlv_1= '(' ( (lv_parameter_2_0= ruleParameter ) ) otherlv_3= ')' ) )
            // InternalHclScope.g:1925:2: ( ( (lv_method_0_0= ruleMethod ) ) otherlv_1= '(' ( (lv_parameter_2_0= ruleParameter ) ) otherlv_3= ')' )
            {
            // InternalHclScope.g:1925:2: ( ( (lv_method_0_0= ruleMethod ) ) otherlv_1= '(' ( (lv_parameter_2_0= ruleParameter ) ) otherlv_3= ')' )
            // InternalHclScope.g:1926:3: ( (lv_method_0_0= ruleMethod ) ) otherlv_1= '(' ( (lv_parameter_2_0= ruleParameter ) ) otherlv_3= ')'
            {
            // InternalHclScope.g:1926:3: ( (lv_method_0_0= ruleMethod ) )
            // InternalHclScope.g:1927:4: (lv_method_0_0= ruleMethod )
            {
            // InternalHclScope.g:1927:4: (lv_method_0_0= ruleMethod )
            // InternalHclScope.g:1928:5: lv_method_0_0= ruleMethod
            {

            					newCompositeNode(grammarAccess.getMethodParameterTriggerAccess().getMethodMethodParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_38);
            lv_method_0_0=ruleMethod();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getMethodParameterTriggerRule());
            					}
            					set(
            						current,
            						"method",
            						lv_method_0_0,
            						"org.xtext.example.hclscope.HclScope.Method");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,34,FOLLOW_3); 

            			newLeafNode(otherlv_1, grammarAccess.getMethodParameterTriggerAccess().getLeftParenthesisKeyword_1());
            		
            // InternalHclScope.g:1949:3: ( (lv_parameter_2_0= ruleParameter ) )
            // InternalHclScope.g:1950:4: (lv_parameter_2_0= ruleParameter )
            {
            // InternalHclScope.g:1950:4: (lv_parameter_2_0= ruleParameter )
            // InternalHclScope.g:1951:5: lv_parameter_2_0= ruleParameter
            {

            					newCompositeNode(grammarAccess.getMethodParameterTriggerAccess().getParameterParameterParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_39);
            lv_parameter_2_0=ruleParameter();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getMethodParameterTriggerRule());
            					}
            					set(
            						current,
            						"parameter",
            						lv_parameter_2_0,
            						"org.xtext.example.hclscope.HclScope.Parameter");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,35,FOLLOW_2); 

            			newLeafNode(otherlv_3, grammarAccess.getMethodParameterTriggerAccess().getRightParenthesisKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMethodParameterTrigger"


    // $ANTLR start "entryRulePort"
    // InternalHclScope.g:1976:1: entryRulePort returns [EObject current=null] : iv_rulePort= rulePort EOF ;
    public final EObject entryRulePort() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePort = null;


        try {
            // InternalHclScope.g:1976:45: (iv_rulePort= rulePort EOF )
            // InternalHclScope.g:1977:2: iv_rulePort= rulePort EOF
            {
             newCompositeNode(grammarAccess.getPortRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePort=rulePort();

            state._fsp--;

             current =iv_rulePort; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePort"


    // $ANTLR start "rulePort"
    // InternalHclScope.g:1983:1: rulePort returns [EObject current=null] : ( (lv_name_0_0= RULE_ID ) ) ;
    public final EObject rulePort() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;


        	enterRule();

        try {
            // InternalHclScope.g:1989:2: ( ( (lv_name_0_0= RULE_ID ) ) )
            // InternalHclScope.g:1990:2: ( (lv_name_0_0= RULE_ID ) )
            {
            // InternalHclScope.g:1990:2: ( (lv_name_0_0= RULE_ID ) )
            // InternalHclScope.g:1991:3: (lv_name_0_0= RULE_ID )
            {
            // InternalHclScope.g:1991:3: (lv_name_0_0= RULE_ID )
            // InternalHclScope.g:1992:4: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            				newLeafNode(lv_name_0_0, grammarAccess.getPortAccess().getNameIDTerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getPortRule());
            				}
            				setWithLastConsumed(
            					current,
            					"name",
            					lv_name_0_0,
            					"org.eclipse.xtext.common.Terminals.ID");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePort"


    // $ANTLR start "entryRuleEvent"
    // InternalHclScope.g:2011:1: entryRuleEvent returns [EObject current=null] : iv_ruleEvent= ruleEvent EOF ;
    public final EObject entryRuleEvent() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEvent = null;


        try {
            // InternalHclScope.g:2011:46: (iv_ruleEvent= ruleEvent EOF )
            // InternalHclScope.g:2012:2: iv_ruleEvent= ruleEvent EOF
            {
             newCompositeNode(grammarAccess.getEventRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEvent=ruleEvent();

            state._fsp--;

             current =iv_ruleEvent; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEvent"


    // $ANTLR start "ruleEvent"
    // InternalHclScope.g:2018:1: ruleEvent returns [EObject current=null] : ( (lv_name_0_0= RULE_ID ) ) ;
    public final EObject ruleEvent() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;


        	enterRule();

        try {
            // InternalHclScope.g:2024:2: ( ( (lv_name_0_0= RULE_ID ) ) )
            // InternalHclScope.g:2025:2: ( (lv_name_0_0= RULE_ID ) )
            {
            // InternalHclScope.g:2025:2: ( (lv_name_0_0= RULE_ID ) )
            // InternalHclScope.g:2026:3: (lv_name_0_0= RULE_ID )
            {
            // InternalHclScope.g:2026:3: (lv_name_0_0= RULE_ID )
            // InternalHclScope.g:2027:4: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            				newLeafNode(lv_name_0_0, grammarAccess.getEventAccess().getNameIDTerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getEventRule());
            				}
            				setWithLastConsumed(
            					current,
            					"name",
            					lv_name_0_0,
            					"org.eclipse.xtext.common.Terminals.ID");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEvent"


    // $ANTLR start "entryRulePortEventTrigger"
    // InternalHclScope.g:2046:1: entryRulePortEventTrigger returns [EObject current=null] : iv_rulePortEventTrigger= rulePortEventTrigger EOF ;
    public final EObject entryRulePortEventTrigger() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePortEventTrigger = null;


        try {
            // InternalHclScope.g:2046:57: (iv_rulePortEventTrigger= rulePortEventTrigger EOF )
            // InternalHclScope.g:2047:2: iv_rulePortEventTrigger= rulePortEventTrigger EOF
            {
             newCompositeNode(grammarAccess.getPortEventTriggerRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePortEventTrigger=rulePortEventTrigger();

            state._fsp--;

             current =iv_rulePortEventTrigger; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePortEventTrigger"


    // $ANTLR start "rulePortEventTrigger"
    // InternalHclScope.g:2053:1: rulePortEventTrigger returns [EObject current=null] : ( ( (lv_port_0_0= rulePort ) ) otherlv_1= '.' ( (lv_event_2_0= ruleEvent ) ) ) ;
    public final EObject rulePortEventTrigger() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_port_0_0 = null;

        EObject lv_event_2_0 = null;



        	enterRule();

        try {
            // InternalHclScope.g:2059:2: ( ( ( (lv_port_0_0= rulePort ) ) otherlv_1= '.' ( (lv_event_2_0= ruleEvent ) ) ) )
            // InternalHclScope.g:2060:2: ( ( (lv_port_0_0= rulePort ) ) otherlv_1= '.' ( (lv_event_2_0= ruleEvent ) ) )
            {
            // InternalHclScope.g:2060:2: ( ( (lv_port_0_0= rulePort ) ) otherlv_1= '.' ( (lv_event_2_0= ruleEvent ) ) )
            // InternalHclScope.g:2061:3: ( (lv_port_0_0= rulePort ) ) otherlv_1= '.' ( (lv_event_2_0= ruleEvent ) )
            {
            // InternalHclScope.g:2061:3: ( (lv_port_0_0= rulePort ) )
            // InternalHclScope.g:2062:4: (lv_port_0_0= rulePort )
            {
            // InternalHclScope.g:2062:4: (lv_port_0_0= rulePort )
            // InternalHclScope.g:2063:5: lv_port_0_0= rulePort
            {

            					newCompositeNode(grammarAccess.getPortEventTriggerAccess().getPortPortParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_40);
            lv_port_0_0=rulePort();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPortEventTriggerRule());
            					}
            					set(
            						current,
            						"port",
            						lv_port_0_0,
            						"org.xtext.example.hclscope.HclScope.Port");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,36,FOLLOW_3); 

            			newLeafNode(otherlv_1, grammarAccess.getPortEventTriggerAccess().getFullStopKeyword_1());
            		
            // InternalHclScope.g:2084:3: ( (lv_event_2_0= ruleEvent ) )
            // InternalHclScope.g:2085:4: (lv_event_2_0= ruleEvent )
            {
            // InternalHclScope.g:2085:4: (lv_event_2_0= ruleEvent )
            // InternalHclScope.g:2086:5: lv_event_2_0= ruleEvent
            {

            					newCompositeNode(grammarAccess.getPortEventTriggerAccess().getEventEventParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_2);
            lv_event_2_0=ruleEvent();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPortEventTriggerRule());
            					}
            					set(
            						current,
            						"event",
            						lv_event_2_0,
            						"org.xtext.example.hclscope.HclScope.Event");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePortEventTrigger"


    // $ANTLR start "entryRuleQualifiedName"
    // InternalHclScope.g:2107:1: entryRuleQualifiedName returns [String current=null] : iv_ruleQualifiedName= ruleQualifiedName EOF ;
    public final String entryRuleQualifiedName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedName = null;


        try {
            // InternalHclScope.g:2107:53: (iv_ruleQualifiedName= ruleQualifiedName EOF )
            // InternalHclScope.g:2108:2: iv_ruleQualifiedName= ruleQualifiedName EOF
            {
             newCompositeNode(grammarAccess.getQualifiedNameRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleQualifiedName=ruleQualifiedName();

            state._fsp--;

             current =iv_ruleQualifiedName.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleQualifiedName"


    // $ANTLR start "ruleQualifiedName"
    // InternalHclScope.g:2114:1: ruleQualifiedName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ValidID_0= ruleValidID (kw= '.' this_ValidID_2= ruleValidID )* ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_ValidID_0 = null;

        AntlrDatatypeRuleToken this_ValidID_2 = null;



        	enterRule();

        try {
            // InternalHclScope.g:2120:2: ( (this_ValidID_0= ruleValidID (kw= '.' this_ValidID_2= ruleValidID )* ) )
            // InternalHclScope.g:2121:2: (this_ValidID_0= ruleValidID (kw= '.' this_ValidID_2= ruleValidID )* )
            {
            // InternalHclScope.g:2121:2: (this_ValidID_0= ruleValidID (kw= '.' this_ValidID_2= ruleValidID )* )
            // InternalHclScope.g:2122:3: this_ValidID_0= ruleValidID (kw= '.' this_ValidID_2= ruleValidID )*
            {

            			newCompositeNode(grammarAccess.getQualifiedNameAccess().getValidIDParserRuleCall_0());
            		
            pushFollow(FOLLOW_41);
            this_ValidID_0=ruleValidID();

            state._fsp--;


            			current.merge(this_ValidID_0);
            		

            			afterParserOrEnumRuleCall();
            		
            // InternalHclScope.g:2132:3: (kw= '.' this_ValidID_2= ruleValidID )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==36) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // InternalHclScope.g:2133:4: kw= '.' this_ValidID_2= ruleValidID
            	    {
            	    kw=(Token)match(input,36,FOLLOW_28); 

            	    				current.merge(kw);
            	    				newLeafNode(kw, grammarAccess.getQualifiedNameAccess().getFullStopKeyword_1_0());
            	    			

            	    				newCompositeNode(grammarAccess.getQualifiedNameAccess().getValidIDParserRuleCall_1_1());
            	    			
            	    pushFollow(FOLLOW_41);
            	    this_ValidID_2=ruleValidID();

            	    state._fsp--;


            	    				current.merge(this_ValidID_2);
            	    			

            	    				afterParserOrEnumRuleCall();
            	    			

            	    }
            	    break;

            	default :
            	    break loop40;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleQualifiedName"


    // $ANTLR start "entryRuleValidID"
    // InternalHclScope.g:2153:1: entryRuleValidID returns [String current=null] : iv_ruleValidID= ruleValidID EOF ;
    public final String entryRuleValidID() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleValidID = null;


        try {
            // InternalHclScope.g:2153:47: (iv_ruleValidID= ruleValidID EOF )
            // InternalHclScope.g:2154:2: iv_ruleValidID= ruleValidID EOF
            {
             newCompositeNode(grammarAccess.getValidIDRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleValidID=ruleValidID();

            state._fsp--;

             current =iv_ruleValidID.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleValidID"


    // $ANTLR start "ruleValidID"
    // InternalHclScope.g:2160:1: ruleValidID returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | this_KEYWORD_1= ruleKEYWORD ) ;
    public final AntlrDatatypeRuleToken ruleValidID() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        AntlrDatatypeRuleToken this_KEYWORD_1 = null;



        	enterRule();

        try {
            // InternalHclScope.g:2166:2: ( (this_ID_0= RULE_ID | this_KEYWORD_1= ruleKEYWORD ) )
            // InternalHclScope.g:2167:2: (this_ID_0= RULE_ID | this_KEYWORD_1= ruleKEYWORD )
            {
            // InternalHclScope.g:2167:2: (this_ID_0= RULE_ID | this_KEYWORD_1= ruleKEYWORD )
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==RULE_ID) ) {
                alt41=1;
            }
            else if ( ((LA41_0>=23 && LA41_0<=24)||LA41_0==37) ) {
                alt41=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 41, 0, input);

                throw nvae;
            }
            switch (alt41) {
                case 1 :
                    // InternalHclScope.g:2168:3: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); 

                    			current.merge(this_ID_0);
                    		

                    			newLeafNode(this_ID_0, grammarAccess.getValidIDAccess().getIDTerminalRuleCall_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalHclScope.g:2176:3: this_KEYWORD_1= ruleKEYWORD
                    {

                    			newCompositeNode(grammarAccess.getValidIDAccess().getKEYWORDParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_KEYWORD_1=ruleKEYWORD();

                    state._fsp--;


                    			current.merge(this_KEYWORD_1);
                    		

                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleValidID"


    // $ANTLR start "entryRuleKEYWORD"
    // InternalHclScope.g:2190:1: entryRuleKEYWORD returns [String current=null] : iv_ruleKEYWORD= ruleKEYWORD EOF ;
    public final String entryRuleKEYWORD() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleKEYWORD = null;


        try {
            // InternalHclScope.g:2190:47: (iv_ruleKEYWORD= ruleKEYWORD EOF )
            // InternalHclScope.g:2191:2: iv_ruleKEYWORD= ruleKEYWORD EOF
            {
             newCompositeNode(grammarAccess.getKEYWORDRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleKEYWORD=ruleKEYWORD();

            state._fsp--;

             current =iv_ruleKEYWORD.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleKEYWORD"


    // $ANTLR start "ruleKEYWORD"
    // InternalHclScope.g:2197:1: ruleKEYWORD returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'initial' | kw= 'history' | kw= 'history*' ) ;
    public final AntlrDatatypeRuleToken ruleKEYWORD() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalHclScope.g:2203:2: ( (kw= 'initial' | kw= 'history' | kw= 'history*' ) )
            // InternalHclScope.g:2204:2: (kw= 'initial' | kw= 'history' | kw= 'history*' )
            {
            // InternalHclScope.g:2204:2: (kw= 'initial' | kw= 'history' | kw= 'history*' )
            int alt42=3;
            switch ( input.LA(1) ) {
            case 23:
                {
                alt42=1;
                }
                break;
            case 37:
                {
                alt42=2;
                }
                break;
            case 24:
                {
                alt42=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 42, 0, input);

                throw nvae;
            }

            switch (alt42) {
                case 1 :
                    // InternalHclScope.g:2205:3: kw= 'initial'
                    {
                    kw=(Token)match(input,23,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getKEYWORDAccess().getInitialKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalHclScope.g:2211:3: kw= 'history'
                    {
                    kw=(Token)match(input,37,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getKEYWORDAccess().getHistoryKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalHclScope.g:2217:3: kw= 'history*'
                    {
                    kw=(Token)match(input,24,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getKEYWORDAccess().getHistoryKeyword_2());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleKEYWORD"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000008872010L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000008070000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000008060000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000008040000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x00000000B8FFF010L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000018F72010L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000018E72010L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000018C72010L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000018872010L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000018070000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000018060000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000018040000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000800010L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x00000020A180D010L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x00000000A000D000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000002000030L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000002001800010L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000002001800030L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000040000010L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000080005002L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000000005002L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000080005012L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000001000000002L});

}