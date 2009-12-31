var rssNotiferFeeds = null;

var loadRssNotiferFeeds = function(_rssNotiferFeeds) {
  rssNotiferFeeds = _rssNotiferFeeds;
  if (rssNotiferFeeds == null) {
    rssNotiferFeeds = [];
  }

  // RSS通知フィード情報更新
  // いったんすべて削除
  var table = $('#rssNotiferTable')[0];
  while(table.rows.length > 1) {
    table.deleteRow(1);
  }

  for(var i = 0, len = rssNotiferFeeds.length; i < len; i++) {
    addRssNotiferRow(rssNotiferFeeds[i]);
  }

}

var addRssNotiferRow = function(feed) {
  var table = $('#rssNotiferTable')[0];
  var tr = table.insertRow(table.rows.length);
  $(tr)
    .append('<td>').children()
      .append($('<span>')
        .attr('class', 'icons')
        .append($('<a href="javascript:void(0)"><img src="./page_cross.gif"></img>削除</a>')
          .click(function() { removeRssNotifer(feed, this.parentNode.parentNode.parentNode) })))
      .append($('<span>').text(feed));
}

var removeRssNotifer = function(feed, elm) {
  elm.parentNode.removeChild(elm);

  for(var i = 0, len = rssNotiferFeeds.length; i < len; i++) {
    if(rssNotiferFeeds[i] == feed){
      rssNotiferFeeds.splice(i,1);
    }
  }

  IrcBotServer.removeRssNotifer(channel, feed);
}


$('#addRssNotiferButton').click(
  function() {
    var feed = $('#rssNotiferFeed').val();

    if (feed == '') {
      alert('RSSフィードが未入力です。');
      return false;
    }
    if ($.inArray(feed, rssNotiferFeeds) != -1) {
      // 既に存在
      alert('既に登録されているRSSフィードです。');
      return false;
    }
    addRssNotiferRow(feed);
    rssNotiferFeeds.push(feed);
    $('#rssNotiferFeed').val('');

    IrcBotServer.addRssNotifer(channel, feed);
  }
);

// DWRのローディング表示用の設定
dwr.util._disabledZoneUseCount = 0;
DWRUtil.useLoadingMessage("Loading..");

// チャンネル名取得
var channel = decodeURIComponent(location.href.substring(location.href.lastIndexOf('?') + 1));
$('#channelName').text(channel);

IrcBotServer.getRssNotiferFeeds(channel, loadRssNotiferFeeds);
