var dateFormat = new DateFormat('yyyy-MM-dd');

var loadLogFiles = function(logFiles) {

  $('#logTitle').text('一覧');

  $('#logViewArea').hide();
  $('#logList').html('').show();
  for (var i = 0, len = logFiles.length; i < len; i++) {
    var date = logFiles[i].replace('.log', '');
    $('#logList').append($('<a style="white-space: nowrap;"></a>')
                 .attr('href', '?' + encodeURIComponent(channel) + '#' + date)
                 .text(date)).append('&nbsp;\n');
  }

  $('#logList').find('a').click(function() {
    var date = $(this).text();
    getDateLog(date);
    return false;
  });
}

var getDateLog = function(date) {
  location.hash = '#' + date;
  LogViewer.getLog(channel, date + '.log', function(log) {
    $('#logTitle').text(date);

    $('#logList').hide();
    $('#logView ul').html('');
    for(var i = 0, len = log.length; i < len; i++) {
      $('#logView ul').append($('<li>').text(log[i]));
    }

    var nextDate = addDay(date, 1);
    var prevDate = addDay(date, -1);

    $('a.nextDate').unbind('click').click(function() {
      var date = $(this).text();
      getDateLog(nextDate);
      return false;
    }).text(nextDate + ' >')
    .attr('href', '?' + encodeURIComponent(channel) + '#' + nextDate);

    $('a.prevDate').unbind('click').click(function() {
      var date = $(this).text();
      getDateLog(prevDate);
      return false;
    }).text('< ' + prevDate)
    .attr('href', '?' + encodeURIComponent(channel) + '#' + prevDate);

    $('#logViewArea').show();

  });
}

var addDay = function(dateStr, day) {
  var date = dateFormat.parse(dateStr);
  date.setDate(date.getDate() + day);

  return dateFormat.format(date);
}

// DWRのローディング表示用の設定
dwr.util._disabledZoneUseCount = 0;
DWRUtil.useLoadingMessage("Loading..");

var query = location.href.substring(location.href.lastIndexOf('?') + 1);

var date;
if (query.indexOf('#') >= 0) {
  // 年月日取得
  date = query.substr(query.indexOf('#') + 1);
}

// チャンネル名取得
var channel = decodeURIComponent(
                date != null ? query.substr(0, query.length - date.length - 1)
                             : query);

$('#channelName').text(channel);

$('#backList').attr('href', '?' + encodeURIComponent(channel))
              .click(function() {
                       location.hash = '#';
                       LogViewer.getFiles(channel, loadLogFiles);
                       return false;
                     });

if (date) {
  getDateLog(date);
} else {
  LogViewer.getFiles(channel, loadLogFiles);
}
