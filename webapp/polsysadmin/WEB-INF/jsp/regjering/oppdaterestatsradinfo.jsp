<%@ page isELIgnored ="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<sql:setDataSource dataSource="jdbc/pvoadmin"/>
<%@ taglib prefix="p" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/jspf/topp.jsp" > </c:import>

<!--<link rel="stylesheet" href="//code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css"> -->


<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/assets/common/css/jquery-ui.css" />" />

<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.0/jquery-ui.js"></script>


<%@ page pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%--
<link rel="stylesheet" href="/resources/demos/style.css">
<style>
    .custom-combobox {
        position: relative;
        display: inline-block;
    }
    .custom-combobox-toggle {
        position: absolute;
        top: 0;
        bottom: 0;
        margin-left: -1px;
        padding: 0;
        /* support: IE7 */
        *height: 1.7em;
        *top: 0.1em;
    }
    .custom-combobox-input {
        margin: 0;
        padding: 0.3em;
    }
</style>

<script>
    (function( $ ) {
        $.widget( "custom.combobox", {
            _create: function() {
                this.wrapper = $( "<span>" )
                        .addClass( "custom-combobox" )
                        .insertAfter( this.element );
                this.element.hide();
                this._createAutocomplete();
                this._createShowAllButton();
            },
            _createAutocomplete: function() {
                var selected = this.element.children( ":selected" ),
                        value = selected.val() ? selected.text() : "";
                this.input = $( "<input>" )
                        .appendTo( this.wrapper )
                        .val( value )
                        .attr( "title", "" )
                        .addClass( "custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left" )
                        .autocomplete({
                            delay: 0,
                            minLength: 0,
                            source: $.proxy( this, "_source" )
                        })
                        .tooltip({
                            tooltipClass: "ui-state-highlight"
                        });
                this._on( this.input, {
                    autocompleteselect: function( event, ui ) {
                        ui.item.option.selected = true;
                        this._trigger( "select", event, {
                            item: ui.item.option
                        });
                    },
                    autocompletechange: "_removeIfInvalid"
                });
            },
            _createShowAllButton: function() {
                var input = this.input,
                        wasOpen = false;
                $( "<a>" )
                        .attr( "tabIndex", -1 )
                        .attr( "title", "Show All Items" )
                        .tooltip()
                        .appendTo( this.wrapper )
                        .button({
                            icons: {
                                primary: "ui-icon-triangle-1-s"
                            },
                            text: false
                        })
                        .removeClass( "ui-corner-all" )
                        .addClass( "custom-combobox-toggle ui-corner-right" )
                        .mousedown(function() {
                            wasOpen = input.autocomplete( "widget" ).is( ":visible" );
                        })
                        .click(function() {
                            input.focus();
// Close if already visible
                            if ( wasOpen ) {
                                return;
                            }
// Pass empty string as value to search for, displaying all results
                            input.autocomplete( "search", "" );
                        });
            },
            _source: function( request, response ) {
                var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
                response( this.element.children( "option" ).map(function() {
                    var text = $( this ).text();
                    if ( this.value && ( !request.term || matcher.test(text) ) )
                        return {
                            label: text,
                            value: text,
                            option: this
                        };
                }) );
            },
            _removeIfInvalid: function( event, ui ) {
// Selected an item, nothing to do
                if ( ui.item ) {
                    return;
                }
// Search for a match (case-insensitive)
                var value = this.input.val(),
                        valueLowerCase = value.toLowerCase(),
                        valid = false;
                this.element.children( "option" ).each(function() {
                    if ( $( this ).text().toLowerCase() === valueLowerCase ) {
                        this.selected = valid = true;
                        return false;
                    }
                });
// Found a match, nothing to do
                if ( valid ) {
                    return;
                }
// Remove invalid value
                this.input
                        .val( "" )
                        .attr( "title", value + " didn't match any item" )
                        .tooltip( "open" );
                this.element.val( "" );
                this._delay(function() {
                    this.input.tooltip( "close" ).attr( "title", "" );
                }, 2500 );
                this.input.autocomplete( "instance" ).term = "";
            },
            _destroy: function() {
                this.wrapper.remove();
                this.element.show();
            }
        });
    })( jQuery );
    $(function() {
        $( "#combobox" ).combobox();
        $( "#toggle" ).click(function() {
            $( "#combobox" ).toggle();
        });
    });
</script>

--%>

<div id="main" class="wide">

    <div class="breadcrumbs">
            Du er her:  <a href="/polsysadmin">Polsys</a>
            >
             <a href="<p:url value="/regjering/index.p"/>">Regjering</a>
            > <a href="<p:url value="/regjering/statsrad.p"/>">Statsråder</a>
            >  Statsråddetaljer
    </div>
 
<br><br>


<c:forEach items="${personinfo}" var="personinfo" begin="0" end="0" >
 <h1>   ${personinfo.etternavn}, ${personinfo.fornavn} </h1>
</c:forEach>

<!--<h2 style="text-decoration:underline; color: black;">Statsrådsaktivitet</h2>   -->


    <table class="zebra, text">
        <caption>Statsrådsaktivitet (Norske_statsraader_ny tabell blir oppdatert)</caption>
        <thead>
        <tr>
            <th></th>
            <th>Embete</th>
            <th>Departement</th>
            <th>Virkeperiode-start dd-mm-yyyy</th>
            <th>Virkeperiode-slutt dd-mm-yyyy</th>
            <th>Eksternkommentar</th>
        </tr>
        </thead>
        <tbody>

        <tr>
            <form action="<c:url value="/regjering/registerstatsradinfo.p" />" method="post">

                <td><input type="submit" value="reg ny" /></td>
                <td>

                    <select name="embetekode">
                        <option value="-1" ></option>
                        <c:forEach items="${embetenavnilist}" var="e">
                            <option value="${e.kode_embete}"> ${fn:escapeXml(e.embete)} </option>
                        </c:forEach>
                    </select>
                        <input style="width: 185px; margin-left: -199px; margin-top: 1px; border: none; float: left;"/>
                    <c:if test="${param.lagret != null}">
                        <h4 style="color:green;">-- Ny statsråd info registrert OK --</h4>
                    </c:if>
                </td>

                <td>
                    <select name="departmentkode" >
                        <option value="-1"></option>
                        <c:forEach items="${departmentnavnilist}" var="e">
                            <option value="${e.departmentkode}"> ${e.departmentnavn}  </option>
                        </c:forEach>
                    </select></td>

                <td><input type="text" size="10" name="startdato" value="" /></td>
                <td><input type="text" size="10" name="sluttdato" value="" /></td>
                <td><input type="text" size="20" name="eksternkommentar" value="" /></td>

                <c:forEach items="${personinfo}" var="personinfo" begin="0" end="0">
                    <td style = "display:none"><input type="hidden"  name="personkode" value="${personinfo.personkode}" /></td>
                    <td style = "display:none"><input type="hidden"  name="etternavn" value="${personinfo.etternavn}" /></td>
                    <td style = "display:none"><input type="hidden"  name="fornavn" value="${personinfo.fornavn}" /></td>
                    <td style = "display:none"><input type="hidden"  name="foedtaar" value="${personinfo.foedtaar}" /></td>
                    <td style = "display:none"><input type="hidden"  name="doedsaar" value="${personinfo.doedsaar}" /></td>
                    <td style = "display:none"><input type="hidden"  name="parti" value="${personinfo.parti}" /></td>
                    <td style = "display:none"><input type="hidden"  name="kode_dep" value="${personinfo.kode_dep}" /></td>
               </c:forEach>

            </form>
        </tr>


        <c:forEach items="${oppdaterepersoninfo}" var="oppdaterepersoninfo">
        <tr>
            <td></td>
            <td>

                <form class="" action="<p:url value="/regjering/lagrestatsradembete.p"/>" method="get" name="frmIndex" >
                    <c:if test="${param.startdato == oppdaterepersoninfo.virkeperiode_start && param.sluttdato == oppdaterepersoninfo.virkeperiode_slutt && param.kode_dep == oppdaterepersoninfo.kode_dep  &&  param.embete_lagret != null}">
                    <h4 style="color:green;">-- Embete ble oppdatert OK --</h4>
                    </c:if>
                    <input type="hidden" name="person_id" value="${oppdaterepersoninfo.personkode}" />
                    <input type="hidden" name="kode_dep" value="${oppdaterepersoninfo.kode_dep}" />
                    <input type="hidden" name="startdato" value="${fn:escapeXml(oppdaterepersoninfo.virkeperiode_start)}" />
                    <input type="hidden" name="sluttdato" value="${fn:escapeXml(oppdaterepersoninfo.virkeperiode_slutt)}" />

                    <input type="text"  name="embete" value="${fn:escapeXml(oppdaterepersoninfo.embete)}" />
                    <input type="submit" name="oppdatere" value="Oppdatere" class="publisherbutton formbutton submitbutton"/>
                </form>

        </td>
        <td>
            <form class="" action="<p:url value="/regjering/lagredepartment/"/>" method="get" name="frmIndex" >
                <c:if test="${param.department_lagret != null}">
                    <h4 style="color:green;">-- Department ble oppdatert OK --</h4>
                </c:if>
                    ${fn:escapeXml(oppdaterepersoninfo.department)}
                <!--  <input type="text" size="50" name="department" value="${fn:escapeXml(oppdaterepersoninfo.department)}" />  -->
              <!--  <input type="submit" name="oppdatere" value="Oppdatere" class="publisherbutton formbutton submitbutton"/> -->
            </form>
        </td>
        <td>
            <form class="" action="<p:url value="/regjering/lagrevirkeperiode_start.p"/>" method="get" name="frmIndex" >
                <c:if test="${param.sluttdato == oppdaterepersoninfo.virkeperiode_slutt && param.startdato == null && param.kode_dep == oppdaterepersoninfo.kode_dep}">
                    <h4 style="color:green;">--  startdato ble oppdatert OK --</h4>
                </c:if>
                <input type="hidden" name="person_id" value="${oppdaterepersoninfo.personkode}" />
                <input type="hidden" name="kode_dep" value="${oppdaterepersoninfo.kode_dep}" />
                <input type="hidden" name="sluttdato" value="${fn:escapeXml(oppdaterepersoninfo.virkeperiode_slutt)}" />


                <input type="text"  name="virkeperiode_start" value="${fn:escapeXml(oppdaterepersoninfo.virkeperiode_start)}" />
                <input type="submit" name="oppdatere" value="Oppdatere" class="publisherbutton formbutton submitbutton"/>
            </form>
        </td>
        <td>
            <form class="" action="<p:url value="/regjering/lagrevirkeperiode_slutt.p"/>" method="get" name="frmIndex" >
                <c:if test="${param.startdato == oppdaterepersoninfo.virkeperiode_start && param.sluttdato == null && param.kode_dep == oppdaterepersoninfo.kode_dep}">
                    <h4 style="color:green;">-- sluttdato ble oppdatert OK --</h4>
                </c:if>
                <input type="hidden" name="sluttdatooppdatert" value="oppdatert" />
                <input type="hidden" name="person_id" value="${oppdaterepersoninfo.personkode}" />
                <input type="hidden" name="kode_dep" value="${oppdaterepersoninfo.kode_dep}" />
                <input type="hidden" name="startdato" value="${fn:escapeXml(oppdaterepersoninfo.virkeperiode_start)}" />

                <input type="text"  name="virkeperiode_slutt" value="${fn:escapeXml(oppdaterepersoninfo.virkeperiode_slutt)}" />
                <input type="submit" name="oppdatere" value="Oppdatere" class="publisherbutton formbutton submitbutton"/>
            </form>
        </td>
            <td>
                <form class="" action="<p:url value="/regjering/lagrestatsradeksternkommentar.p"/>" method="get" name="frmIndex" >
                    <c:if test="${param.startdato == oppdaterepersoninfo.virkeperiode_start && param.sluttdato == oppdaterepersoninfo.virkeperiode_slutt && param.kode_dep == oppdaterepersoninfo.kode_dep  &&  param.eksternkommentar_lagret != null}">
                        <h4 style="color:green;">--  eksternkommentar ble oppdatert OK --</h4>
                    </c:if>
                    <input type="hidden" name="person_id" value="${oppdaterepersoninfo.personkode}" />
                    <input type="hidden" name="kode_dep" value="${oppdaterepersoninfo.kode_dep}" />
                    <input type="hidden" name="startdato" value="${fn:escapeXml(oppdaterepersoninfo.virkeperiode_start)}" />
                    <input type="hidden" name="sluttdato" value="${fn:escapeXml(oppdaterepersoninfo.virkeperiode_slutt)}" />

                    <input type="text"  name="eksternkommentar" value="${fn:escapeXml(oppdaterepersoninfo.eksternkommentar)}" />
                    <input type="submit" name="oppdatere" value="Oppdatere" class="publisherbutton formbutton submitbutton"/>
                </form>
            </td>
        </tr>
        </c:forEach>
        </tbody>
    </table>


 <!--
 <br><br><br><br><br><br><br><br><br><br><br>

<c:forEach items="${oppdaterepersoninfo}" var="oppdaterepersoninfo">
<table class="text" border="0">
<tbody>
<tr>
<td style="vertical-align: top"><strong>NFI Prosjektnummer:</strong></td>
<td class="tdtext">${oppdaterepersoninfo.etternavn}</td>
</tr>

<tr>
<td></td>
<td class="tdtext"></td>
</tr>

<tr>
<td style="vertical-align: top"><strong>NFR Prosjektnummer:</strong></td>
<td class="tdtext">${oppdaterepersoninfo.fornavn}</td>
</tr>
<tr>
<td></td>
<td class="tdtext"></td>
</tr>

<tr class="odd">
<td style="vertical-align: top;  color: #37527F;"><strong>Prosjekttittel:</strong></td>
<td>

<form class="" action="<p:url value="/lagreprosjekttittel/"/>" method="get" name="frmIndex" >
    <c:if test="${param.tittel_lagret != null}">
    <h4 style="color:green;">-- Prosjekttittel ble oppdatert OK --</h4>
     </c:if>
	<table class="webform_table" >
		<tr >
		<td class="webform_field"><input type="hidden" name="prosjketid" value="${prosjektdetaljer.prosjektid}" /></td>
		<td class="webform_field"><input type="hidden" name="nfrnr" value="${prosjektdetaljer.nfrnr}" /></td>
		</tr>
		<tr>
		<td class="webform_field_multi"><textarea name="melding" class="onlineform" >${fn:escapeXml(prosjektdetaljer.tittel_no)}</textarea></td>
		</tr>
		<tr>
		<td><input type="submit" name="oppdatere" value="Oppdatere" class="publisherbutton formbutton submitbutton"/></td>
		</tr>
	</table>
	</form>    

</td>

</tr>

<tr>
<td></td>
<td class="tdtext"></td>
</tr>

<tr>
<td style="vertical-align: top"><strong>Program:</strong></td>
<td class="tdtext">${prosjektdetaljer.akronym}(${prosjektdetaljer.forskProg_no})</td>
</tr>
<tr>
<td style="vertical-align: top"><strong>Prosjekttypemerking:</strong></td>
<td class="tdtext">${prosjektdetaljer.kode}</td>
</tr>
<tr>
<td></td>
<td class="tdtext"></td>
</tr>

<tr>
<td style="vertical-align: top"><strong>Prosjekttypemerking tittle:</strong></td>
<td class="tdtext">${prosjektdetaljer.klasse_no}</td>
</tr>

<tr>
<td style="vertical-align: top"><strong>Prosjektperiode:</strong></td>
<td class="tdtext">${prosjektdetaljer.prosj_start} - ${prosjektdetaljer.prosj_slutt}</td>
</tr>
<tr>
<td></td>
<td class="tdtext"></td>
</tr>

<tr>
<td style="vertical-align: top; "><strong>Prosjektleder:</strong></td>
<td class="tdtext">
<strong>Navn:</strong> ${prosjektdetaljer.etternavn}, ${prosjektdetaljer.fornavn}

 <form class="" action="<p:url value="/lagreprosjeklederepost/"/>" method="get" name="frmIndex" >
    <c:if test="${param.epost_lagret != null}">
    <h4 style="color:green;">-- Prosjektleder epost ble oppdatert OK --</h4>
     </c:if>
	<table class="webform_table" >
      <CAPTION>Epost:</CAPTION>
		<tr >
		<td class="webform_field"><input type="hidden" name="prosjketid" value="${prosjektdetaljer.prosjektid}" /></td>
		<td class="webform_field"><input type="hidden" name="nfrnr" value="${prosjektdetaljer.nfrnr}" /></td>
		</tr>
		<tr>
		<td class="webform_field_multi"><textarea name="melding" class="onlineform">${fn:escapeXml(prosjektdetaljer.epost)}</textarea></td>
		</tr>
		<tr>
		<td><input type="submit" name="oppdatere" value="Oppdatere" class="publisherbutton formbutton submitbutton"/></td>
		</tr>
	</table>
	</form>


<br><strong>Institusjonnavn:</strong> ${prosjektdetaljer.institusjonnavn}
</td>
</tr>

<tr>
<td style="vertical-align: top"><strong><br>Prosjektansvarlig institusjon:</strong></td>
<td class="tdtext">
    <br>
 <p><strong>Navn:</strong> ${prosjektdetaljer.ansv_etternavn}, ${prosjektdetaljer.ansv_fornavn}
<br><strong>Epost:</strong> ${prosjektdetaljer.asnv_epost}
<br><strong>Institusjonnavn:</strong> ${prosjektdetaljer.ansv_institusjonnavn}
<br><strong>Gateadresse:</strong> ${prosjektdetaljer.ansv_adresse}
<br><strong>Postnummer:</strong> ${prosjektdetaljer.ansv_postnummer}
<br><strong>Poststed:</strong> ${prosjektdetaljer.ansv_poststed}     
</p>
</td>
</tr>

<tr>
<td style="vertical-align: top; color: #37527F;"><strong>Prosjektbeskrivelse:</strong></td>
<td class="tdtext">

<form class="" action="<p:url value="/lagreprosjektbeskrivelse/"/>" method="get" name="frmIndex" >
<c:if test="${param.beskrivelse_lagret != null}">
<h4 style="color:green;">-- Prosjektbeskrivelse ble oppdatert OK --</h4>
 </c:if>
	<table class="webform_table" >
		<tr >
		<td class="webform_field"><input type="hidden" name="prosjketid" value="${prosjektdetaljer.prosjektid}" /></td>
		<td class="webform_field"><input type="hidden" name="nfrnr" value="${prosjektdetaljer.nfrnr}" /></td>
		</tr>
		<tr>
		<td class="webform_field_multi_besk"><textarea name="melding" class="onlineform">${fn:escapeXml(prosjektdetaljer.prosjektbesk_no)}</textarea></td>
		</tr>
		<tr>
		<td ><input type="submit" name="oppdatere" value="Oppdatere" class="publisherbutton formbutton submitbutton"/></td>
		</tr>
	</table>
	</form>
</td>
</tr>

</tbody>
</table>

 </c:forEach>

-->
</div>

<c:import url="/WEB-INF/jspf/bunn.jsp" />
