/*
 * Simplified Chinese translation
 * By DavidHu
 * 09 April 2007
 */

L5.UpdateManager.defaults.indicatorText = '<div class="loading-indicator">������...</div>';

if(L5.View){
   L5.View.prototype.emptyText = "";
}

if(L5.grid.GridPanel){
   L5.grid.GridPanel.prototype.ddText = "{0} ѡ����";
}

if(L5.TabPanelItem){
   L5.TabPanelItem.prototype.closeText = "�ر�";
}

if(L5.form.Field){
   L5.form.Field.prototype.invalidText = "����ֵ�Ƿ�";
}

Date.monthNames = [
   "һ��",
   "����",
   "����",
   "����",
   "����",
   "����",
   "����",
   "����",
   "����",
   "ʮ��",
   "ʮһ��",
   "ʮ����"
];

Date.dayNames = [
   "��",
   "һ",
   "��",
   "��",
   "��",
   "��",
   "��"
];

if(L5.MessageBox){
   L5.MessageBox.buttonText = {
      ok     : "ȷ��",
      cancel : "ȡ��",
      yes    : "��",
      no     : "��"
   };
}

if(L5.util.Format){
   L5.util.Format.date = function(v, format){
      if(!v) return "";
      if(!(v instanceof Date)) v = new Date(Date.parse(v));
      return v.dateFormat(format || "y��m��d��");
   };
}

if(L5.DatePicker){
   L5.apply(L5.DatePicker.prototype, {
      todayText         : "����",
      minText           : "��������С����֮ǰ",
      maxText           : "�������������֮��",
      disabledDaysText  : "",
      disabledDatesText : "",
      monthNames        : Date.monthNames,
      dayNames          : Date.dayNames,
      nextText          : '���� (Control+Right)',
      prevText          : '���� (Control+Left)',
      monthYearText     : 'ѡ��һ���� (Control+Up/Down ���ı���)',
      todayTip          : "{0} (�ո��ѡ��)",
      format            : "y��m��d��",
      okText            : "ȷ��",
      cancelText        : "ȡ��"
   });
}

if(L5.PagingToolbar){
   L5.apply(L5.PagingToolbar.prototype, {
      beforePageText : "��",
      afterPageText  : "ҳ�� {0} ҳ",
      firstText      : "��һҳ",
      prevText       : "ǰһҳ",
      nextText       : "��һҳ",
      lastText       : "���ҳ",
      refreshText    : "ˢ��",
      displayMsg     : "��ʾ {0} - {1}���� {2} ��",
      emptyMsg       : 'û��������Ҫ��ʾ'
   });
}

if(L5.form.TextField){
   L5.apply(L5.form.TextField.prototype, {
      minLengthText : "�����������С������ {0}",
      maxLengthText : "�����������󳤶��� {0}",
      blankText     : "��������Ϊ������",
      regexText     : "",
      emptyText     : null
   });
}

if(L5.form.NumberField){
   L5.apply(L5.form.NumberField.prototype, {
      minText : "�����������Сֵ�� {0}",
      maxText : "������������ֵ�� {0}",
      nanText : "{0} ������Ч��ֵ"
   });
}

if(L5.form.DateField){
   L5.apply(L5.form.DateField.prototype, {
      disabledDaysText  : "����",
      disabledDatesText : "����",
      minText           : "������������ڱ����� {0} ֮��",
      maxText           : "������������ڱ����� {0} ֮ǰ",
      invalidText       : "{0} ����Ч������ - ������ϸ�ʽ�� {1}",
      format            : "y��m��d��"
   });
}

if(L5.form.ComboBox){
   L5.apply(L5.form.ComboBox.prototype, {
      loadingText       : "����...",
      valueNotFoundText : undefined
   });
}

if(L5.form.VTypes){
   L5.apply(L5.form.VTypes, {
      emailText    : '������������ǵ����ʼ���ַ����ʽ�磺 "user@domain.com"',
      urlText      : '�������������URL��ַ����ʽ�磺 "http:/'+'/www.domain.com"',
      alphaText    : '��������ֻ�ܰ����ַ���_',
      alphanumText : '��������ֻ�ܰ����ַ�,���ֺ�_'
   });
}

if(L5.grid.GridView){
   L5.apply(L5.grid.GridView.prototype, {
      sortAscText  : "����",
      sortDescText : "����",
      lockText     : "����",
      unlockText   : "������",
      columnsText  : "��"
   });
}

if(L5.grid.PropertyColumnModel){
   L5.apply(L5.grid.PropertyColumnModel.prototype, {
      nameText   : "����",
      valueText  : "ֵ",
      dateFormat : "y��m��d��"
   });
}

if(L5.layout.BorderLayout && L5.layout.BorderLayout.SplitRegion){
   L5.apply(L5.layout.BorderLayout.SplitRegion.prototype, {
      splitTip            : "�϶����ı�ߴ�.",
      collapsibleSplitTip : "�϶����ı�ߴ�. ˫������."
   });
}

L5.connectionError="���ӷ�����ʧ��";
L5.turnPage="ҳ��̫���̫С";
L5.turnPageFirst="�Ѿ��ǵ�һҳ";
L5.turnPageLast="�Ѿ������һҳ";
L5.di="��";
L5.hang="��";
L5.lie="��";
L5.valError="У�����";
L5.parseReturnError="��������ֵ�쳣";
L5.loadDateError="�������ݳ���";
L5.radio="ѡ��";
L5.notfind="�Ҳ���";