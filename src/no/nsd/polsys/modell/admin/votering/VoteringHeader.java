package no.nsd.polsys.modell.admin.votering;

/**
 * Created with IntelliJ IDEA.
 * User: et
 * Date: 30.05.13
 * Time: 13:06
 * To change this template use File | Settings | File Templates.
 */

import java.util.ArrayList;


//the root-element of our order XML file
public class VoteringHeader {

    String voteringId = null;
    String typeId = null;
    Double orderTotal = null;
    ArrayList<stortingsperiode> orderDetailList;

    public String getVoteringId() {
        return voteringId;
    }

    //defines the XML element name and it's namespace
    public void setVoteringId(String voteringId) {
        this.voteringId = voteringId;
    }

    public String getTypeId() {
        return typeId;
    }

    //this makes it an attribute for the parent node
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
    public Double getOrderTotal() {
        return orderTotal;
    }


    public ArrayList<stortingsperiode> getVoteringParametreDetailList() {
        return orderDetailList;
    }

    //adds a wrapper element around the XML representation

    //override the name for the XML element
    public void setOrderDetailList(ArrayList<stortingsperiode> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
}
