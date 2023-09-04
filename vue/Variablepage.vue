<template>
  <spinner v-if="loadingvariable"/>
  <!--  DATASETT  title og search box-->
  <div
    v-if="variablemetadata"
    class="wrapper-medium wrapper-space"
  >
    <!--    title -->
    <p>
      <span class="itemHeading"><span
        v-if="lang"
        class="itemHeading"
      >DATASET: </span><span
        v-else
        class="itemHeading"
      >DATASETT: </span> </span>
      <span class="itemName"><router-link
        :to="{ name: 'Study', params: {l:$route.params.l, id: variablemetadata.study.id }}">
        <span v-if="lang && variablemetadata.study.citation.title.en">{{
            variablemetadata.study.citation.title.en
          }}</span>
        <span v-else-if="variablemetadata.study.citation.title.no">{{ variablemetadata.study.citation.title.no }}</span>
      </router-link></span>
    </p>
    <div v-if="variablemetadata.dataFile.variableGroups[0]">
      <searchbox :variablemetadata="variablemetadata"/>
    </div>
  </div>

  <!--  Varibale label and name with literalQuestion-->
  <section class="pageSummary__bg">
    <PageModuleVariableInfo :variablemetadata="variablemetadata"/>
  </section>

  <!--error --  -->
  <div
    v-if="error"
    class="blockContent wrapper-medium wrapper-space"
  >
    <p>{{ error }}</p>
  </div>

  <!-- Chart and Map -->
  <section class="pageSummarygraph pageSummary__bg">
    <!-- 1  info about weighted Variable  -->
    <div v-if=" checkanalysefreqtabmainvariable && !error">
      <div
        v-if="variablemetadata"
        class="wrapper-medium wrapper-space"
        style="font-size: 0.8rem"
      >
        <span v-if="variablemetadata && variablemetadata.dataFile.defaultWeight !=null">
          <span v-if="lang"> The results are weighted by the variable: </span><span v-else>Resultatene er vektet med variabelen: </span><span
          style="font-style: italic">{{ variablemetadata.dataFile.defaultWeight.name.no }} -
            <span v-if="lang">{{ variablemetadata.dataFile.defaultWeight.label.en }}</span><span
            v-else>{{ variablemetadata.dataFile.defaultWeight.label.no }}</span>
          </span>
        </span>
      </div>
    </div>

    <!-- 2 descrete varibale  side -->
    <div v-if=" variablemetadata && !variablemetadata.isContinuous && checkanalysefreqtabmainvariable && !error ">
      <section :class="wrappermediumClass()">
        <div
          class="featuredLinks"
          style="margin-top: 0px;  margin-bottom: 0px;"
        >
          <div
            class="featuredLinks__item"
            :style="oneVariableClass()"
          >
            <div style="width: 100%">
              <Barchart :variablemetadata="variablemetadata"/>
            </div>
          </div>
          <Stackedbarchart :variablemetadata="variablemetadata"/>
        </div>
      </section>
      <span v-if="variablemetadata.dataFile.geographicVariables[0] !=null && $route.params.id === variablemetadata.id ">
        <div
          class="wrapper-medium wrapper-space"
          style=" padding-top: 30px;  padding-bottom: 30px;"
        >
          <norwaymapdescvar :variablemetadata="variablemetadata"/></div>
      </span>
    </div>

    <!-- 3 continous variable side  -->
    <div v-else-if=" variablemetadata && variablemetadata.isContinuous && checkonecontvariableside && !error ">
      <section :class="wrappermediumClass()">
        <div
          class="featuredLinks"
          style="margin-top: 0px;  margin-bottom: 0px;"
        >
          <div
            class="featuredLinks__item"
            :style="oneVariableClass()"
          >
            <div style="width: 100%">
              <Boxplotchart :variablemetadata="variablemetadata"/>
            </div>
          </div>
          <Boxplotchartmedconcept :variablemetadata="variablemetadata"/>
        </div>
      </section>
      <span v-if="variablemetadata.dataFile.geographicVariables[0] !=null && $route.params.id === variablemetadata.id ">
        <div
          class="wrapper-medium wrapper-space"
          style=" padding-top: 30px;  padding-bottom: 30px;"
        >
          <div style="width: 100%">  <norwaymapcontvariable :variablemetadata="variablemetadata"/></div>
        </div>
      </span>
    </div>
  </section>

  <!--    Detailed variable information for variable-->
  <div
    v-if="variablemetadata"
    class="wrapper-medium wrapper-medium"
  >
    <div class="accordionList">
      <div class="accordionList__list-item">
        <input
          :id="parseInt($route.params.id)+1"
          type="checkbox"
        >
        <label
          class="accordion__label"
          :for="parseInt($route.params.id)+1"
        >
          <p><span v-if="lang">Detailed variable information</span><span v-else>Detaljert variabelinformasjon</span></p>
        </label>
        <div class="accordion__content blockContent">
          <PageModuleVariableInfo :variablemetadata="variablemetadata"></pagemodulevariableinfo>
            <span v-if="variablemetadata && !variablemetadata.isContinuous && $route.params.id === variablemetadata.id">
              <Deskvariableutenvekt :variablemetadata="variablemetadata"/></span>
            <span
              v-else-if="variablemetadata && variablemetadata.isContinuous && $route.params.id === variablemetadata.id">
              <Contvariableutenvekt :variablemetadata="variablemetadata"/></span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Spinner from "@/components/Spinner";
import {useI18n} from 'vue-i18n'
import Barchart from "@/views/analysispages/variablepage/discrete/barchart";
import Boxplotchart from "@/views/analysispages/variablepage/continuous/boxplotchart";
import Deskvariableutenvekt from "@/views/analysispages/variablepage/discrete/deskvariableutenvekt";
import Contvariableutenvekt from "@/views/analysispages/variablepage/continuous/contvariableutenvekt";
import Boxplotchartmedconcept from "@/views/analysispages/variablepage/continuous/boxplotchartmedconcept";
import Stackedbarchart from "@/views/analysispages/variablepage/discrete/stackedbarchart";
import Norwaymapdescvar from "@/views/analysispages/variablepage/discrete/norwaymapdescvar";
import Norwaymapcontvariable from "@/views/analysispages/variablepage/continuous/norwaymapcontvariable";
import Searchbox from "@/components/analysis/searchbox";
import PageModuleVariableInfo from "@/components/pageModules/PageModuleVariableInfo";
import {getDefaultWeight} from '@/lib/pagesHelper.ts'

export default {
  name: "Variablepage",
  components: {
    PageModuleVariableInfo,
    Searchbox,
    Norwaymapcontvariable,
    Norwaymapdescvar,
    Stackedbarchart,
    Boxplotchartmedconcept,
    Contvariableutenvekt,
    Deskvariableutenvekt,
    Spinner,
    Barchart,
    Boxplotchart,
  },
  props: {
    id: {type: String, required: true},
  },
  setup() {
    const {t, locale} = useI18n();
    return {t, locale}
  },
  data() {
    return {
      loadingvariable: false,
      error: '',
      showgraph: 'graph',
      selectedvar: '',
      backgroundConcepts: []
    }
  },
  computed: {
    lang() {
      return this.$route.params.l === 'en'
    },
    variablemetadata() {
      return this.$store.getters['analyse/variableMetadata']
    },
    checkanalysefreqtabmainvariable() {
      return this.$store.getters["analyse/analysemainVariablefrequencytabulation"];
    },
    checkonecontvariableside() {
      return this.$store.getters["analyse/onecontvariableside"];
    },
  },
  watch: {
    variablemetadata() {
      this.getAllVariableInfo();
    },
  },
  created() {
    this.getLang()
    this.getAllVariableInfo();
  },
  methods: {
    async getLang() {
      if (this.$route.params.l !== 'en' && this.$route.params.l !== '') {
        return this.$router.push({name: 'NotFound', params: {catchAll: '404'}});
      }
    },
    async getVariableMetadata(id) {
      this.loadingvariable = true;
      const response = await this.$store.dispatch("analyse/getVariableMetadata", {
        payload1: id,
      });
      this.loadingvariable = false;
      return response;
    },
    async getAllVariableInfo() {
      await this.getVariableMetadata(this.$route.params.id)
      if (this.variablemetadata) {
        await this.getFrequencyTabulationMainVariable(this.variablemetadata.dataFile.id, this.variablemetadata.dataFile.version, this.variablemetadata.name.no, getDefaultWeight(this.variablemetadata, this.$route.params.l));
        await this.getOnecontvariablesideData(this.variablemetadata.dataFile.id, this.variablemetadata.dataFile.version, this.variablemetadata.name.no, getDefaultWeight(this.variablemetadata, this.$route.params.l));
      }
      await this.getAllBackgroundConcepts();
    },

    /*******get all background concept variables for a variable********/
    getAllBackgroundConcepts() {
      this.backgroundConcepts = []
      if (this.variablemetadata && this.variablemetadata.dataFile !== null && this.variablemetadata.dataFile.backgroundVariables) {
        for (let i = 0; i < this.variablemetadata.dataFile.backgroundVariables.length; i++) {
          let langvariablelabel
          if (this.$route.params.l !== '' && this.$route.params.l === 'en') {
            langvariablelabel = this.variablemetadata.dataFile.backgroundVariables[i].label.en
          } else {
            langvariablelabel = this.variablemetadata.dataFile.backgroundVariables[i].label.no
          }

          this.backgroundConcepts.push({
            id: this.variablemetadata.dataFile.backgroundVariables[i].id,
            name: this.variablemetadata.dataFile.backgroundVariables[i].name.no,
            variablelabel: langvariablelabel,
            conceptid: this.variablemetadata.dataFile.backgroundVariables[i].concept.id,
            label: this.variablemetadata.dataFile.backgroundVariables[i].concept.label.no,
          });
        }
      }
    },

    /****************sjekk for open spårmøl variable*************************/

    async getFrequencyTabulationMainVariable(datafileId, version, breakvariable, weightvariable) {
      const payload = {
        datafileId: datafileId,
        datafileVersion: parseInt(version),
        agencyId: 'NO_NSD',
        /* instance: "SANDBOX",*/
        relativeRow: true,
        relativeCell: true,
        relativeCol: true,
        breakVariables: [breakvariable],
        weightVariable: weightvariable || null,
        metadataLanguage: "no"
      };

      const response = await this.$store.dispatch(
        "analyse/AnalyseMainVariableFrequencyTabulation",
        {
          payload: payload,
        }
      ).catch(e => {
        this.error = e.graphQLErrors[0].extensions.body
        /*if (this.lang) {
             this.error = "The variable is empty, illegal or contains only missing values."
          } else {
            this.error = "Variabelen er tom, ulovlig eller inneholder kun manglende verdier."
          }*/
      })
      return response;
    },


    async getOnecontvariablesideData(datafileId, version, variablename, weightvariable) {
      const payload = {
        datafileId: datafileId,
        datafileVersion: parseInt(version),
        agencyId: "NO_NSD",
        measures: ["MEAN", "COUNT", "STD", "MIN", "P25", "P50", "P75", "MAX"],
        variableName: variablename,
        weightVariable: weightvariable || null,
        metadataLanguage: "no"
      };
      const response = await this.$store.dispatch(
        "analyse/GetOnecontvariableside", {payload: payload,}
      ).catch(e => {
        this.error = e.graphQLErrors[0].extensions.body
        /*if (this.lang) {
             this.error = "The variable is empty, illegal or contains only missing values."
          } else {
            this.error = "Variabelen er tom, ulovlig eller inneholder kun manglende verdier."
          }*/
      })
      return response;
    },

    wrappermediumClass() {
      if (this.backgroundConcepts.length > 0) {
        return 'wrapper-large'
      } else return 'wrapper-medium'
    },

    oneVariableClass() {
      if (this.backgroundConcepts.length > 0) {
        return 'background-color: white; transform: none'
      } else return 'background-color: white; transform: none; padding: 30px; width:480px'
    },

  }
}


</script>

<style scoped>
@import '../../../assets/css/featuredLinks.css';
@import '../../../assets/css/selectionbox.css';

ul {
  padding-left: 0;
}

.blockContent ul li {
  text-indent: 0;
}

.blockContent ul > li::before {
  content: none;
}

.pageSummary__bg {
  background-color: #F5F5F5;
}

.pageSummarygraph {
  padding-top: var(--space-small);
  padding-bottom: var(--space-small);
}

.pageSummaryheader {
  padding-top: var(--space-large);
  padding-bottom: 0px;
}

/******************************************************/
/*******************************************************/
/* The switch - the box around the slider */
.switch {
  position: relative;
  display: inline-block;
  width: 100px;
  height: 34px;
}

/* Hide default HTML checkbox */
.switch input {
  display: none;
}

/* The slider */
.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  -webkit-transition: .4s;
  transition: .4s;
}

.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  -webkit-transition: .4s;
  transition: .4s;
}

input:checked + .slider {
  background-color: #b01311;
}

input:focus + .slider {
  box-shadow: 0 0 1px #b01311;
}

input:checked + .slider:before {
  -webkit-transform: translateX(26px);
  -ms-transform: translateX(26px);
  transform: translateX(66px);
}

/* Rounded sliders */
.slider.round {
  border-radius: 34px;
}

.slider.round:before {
  border-radius: 50%;
}


/**NEW for search box****/

.selectionbox__input {
  width: 100%;
  border: none;
  padding: var(--space-small) var(--space-x-small);
  font-size: var(--font-size-small);
  font-family: var(--font-family-serif);
}

.selectionbox__input:focus {
  outline: var(--outline-width) solid #2dbdbd;
}

.selectdiv {
  position: relative;
  /*Don't really need this just for demo styling*/

  /* float: left;*/
  min-width: 200px;
  /* margin: 50px 33%;*/
}

.selectdiv:after {
  content: url(../../../components/icons/bottomarraw.png);
  font: normal normal normal 17px/1 FontAwesome;
  color: #0ebeff;
  right: 11px;
  /*top: 20px;*/
  height: 34px;
  padding: 15px 0px 0px 8px;
  border-left: 1px solid #0ebeff;
  position: absolute;
  pointer-events: none;
  margin-top: 5px;
}

/* IE11 hide native button (thanks Matt!) */
select::-ms-expand {
  display: none;
}

.selectdiv select {
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  /* Add some styling */

  display: inherit;
  /* width: 100%;
   min-width: 320px;*/
  height: 50px;
  float: left;
  /* margin: 5px 0px;*/
  padding: 0px 24px;
  font-size: 16px;
  line-height: 1.75;
  color: #333;
  background-color: #ffffff;
  background-image: none;
  border: 1px solid #0ebeff;
  -ms-word-break: normal;
  word-break: normal;
}

.ct-find-service {
  /* background: #fef0e6;*/
  font-family: 'stenciletta-solid', sans-serif;
  /*padding: 20px 25px;*/
  margin-top: 50px;
  margin-bottom: 15px;
  margin-top: 0;
  /*padding-left: 25px;
  padding-right: 25px;*/
}

.subAccordion__content {
  padding: 0px;
}

/*.blockContent ul {
  padding-left: 3rem;
}*/

@media screen and (max-width: 674px) {
  .selectionbox {
    margin-top: 0px;
    margin-bottom: 40px;
  }

  .ct-find-service {
    /*padding: 10px 0px;*/
    padding-bottom: 10px;
  }

  .selectionbox__item {
    padding-bottom: var(--space-medium);
  }
}

/*************************************************/
/*************************************************/


.tabs-nav {
  list-style: none;
  margin: 0;
  padding: 0;
}

/*.tabs-nav li:first-child a {
 border-right: 0;
  -moz-border-radius-topleft: 6px;
  -webkit-border-top-left-radius: 6px;
  border-top-left-radius: 6px;
}*/
.tabs-nav .tab-active a {
  background: hsl(0, 100%, 100%);
  border-bottom-color: hsla(0, 0%, 0%, 0);
  color: hsl(85, 54%, 51%);
  cursor: default;
}

.tabs-nav a {
  background: hsl(120, 11%, 96%);
  border: 1px solid hsl(210, 6%, 79%);
  color: hsl(215, 6%, 57%);
  display: block;
  font-size: 11px;
  font-weight: bold;
  /* height: 40px;*/
  line-height: 28px;
  text-align: center;
  text-transform: uppercase;
  width: 140px;
}


/****************************************************/
/****************************************************/
/****************************************************/
.tabs {
  max-width: 90%;
  float: none;
  list-style: none;
  padding: 0;
  margin: 75px auto;
  /* border-bottom: 4px solid #ccc;*/
}

.tabs:after {
  content: '';
  display: table;
  clear: both;
}

.tabs input[type=radio] {
  display: none;
}

.tabs label {
  display: block;
  float: left;
  width: 15.3333%;
  /* color: #ccc;*/
  font-size: 14px;
  font-weight: normal;
  text-decoration: none;
  text-align: center;
  cursor: pointer;
  line-height: 28px;
  /*box-shadow: inset 0 4px #ccc;
  border-bottom: 4px solid #ccc;*/
  border: 1px solid hsl(210, 6%, 79%);

}

/*
.tabs label span {
    display: none;
}
*/
.tabs label i {
  padding: 5px;
  margin-right: 0;
}

.tabs label:hover {
  color: #3498db;
  box-shadow: inset 0 2px #3498db;
  border-bottom: 2px solid #3498db;
}

.tab-content {
  display: none;
  width: 100%;
  float: left;
  padding: 15px;
  box-sizing: border-box;
  background-color: #ffffff;
}

.tab-content * {
  -webkit-animation: scale 0.7s ease-in-out;
  -moz-animation: scale 0.7s ease-in-out;
  animation: scale 0.7s ease-in-out;
}

#tab1:checked ~ #tab-content1,
#tab2:checked ~ #tab-content2,
#tab3:checked ~ #tab-content3 {
  display: block;
}

.tabscontent {
  background: #FFF;
  /*box-shadow: inset 0 2px #3498db;
  border-bottom: 2px solid #3498db;*/
  color: #3498db;
}

@media (min-width: 674px) {
  .tabs i {
    padding: 5px;
    margin-right: 10px;
  }

  .tabs label span {
    display: inline-block;
  }

  .tabs {
    max-width: 750px;
    /*margin: 50px auto;*/
    margin: 10px 0px;
  }
}

@media screen and (max-width: 320px) {
  .tabs label {
    float: none;
    width: 25.3333%;
  }
}


</style>