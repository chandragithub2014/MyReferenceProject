package com.ref.listtypes.expandable.models

class ExpandableCountryModel {
    companion object{
        const val PARENT = 1
        const val CHILD = 2
       /* const val BUTTONLAYOUT = 3
        const val REMOVELIST = 4
        const val EMPTYLAYOUT = 5*/
    }
   lateinit var countryParent: StateCapital.Country
    var type : Int
    lateinit var countryChild : StateCapital.Country.State
    var isExpanded : Boolean
  //  var isPauseShown : Boolean
    private var isCloseShown : Boolean
   // var isLocked : Boolean

    constructor( type : Int, countryParent: StateCapital.Country, isExpanded : Boolean = false,isCloseShown : Boolean = false ){
        this.type = type
        this.countryParent = countryParent
        this.isExpanded = isExpanded
        this.isCloseShown = isCloseShown

    }


    constructor(type : Int, countryChild : StateCapital.Country.State, isExpanded : Boolean = false,isCloseShown : Boolean = false){
        this.type = type
        this.countryChild = countryChild
        this.isExpanded = isExpanded
        this.isCloseShown = isCloseShown


    }
}