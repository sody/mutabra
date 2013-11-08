/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */
Tapestry.ZONE_REFRESH_EVENT = "tapestry:zonerefresh";

Tapestry.Initializer.zoneRefresh = function(params)
{
   var zoneRefreshId = params.id + "_refresh";
   
   // This will prevent any more instantiation of  PeriodicalExecuter
   if($(zoneRefreshId))
   {
      return;
   }
   
   // Create a new element and place it at the end of the document. Then we use
   // it for refreshing the zone
   var zoneRefresh = document.createElement("div");
   zoneRefresh.id = zoneRefreshId;
   
   // Link zoneRefresh element to zone
   $T(zoneRefresh).zoneId = params.id;
   document.body.appendChild(zoneRefresh);
   
   // Connect event to zone
   Tapestry.Initializer.updateZoneOnEvent(Tapestry.ZONE_REFRESH_EVENT, zoneRefresh, params.id, params.URL);

   //Create timer
   var timer = new PeriodicalExecuter(function(e)
   {
      zoneRefresh.fire(Tapestry.ZONE_REFRESH_EVENT);
   }, params.period);

   
   //Clear the timer before unload
   Event.observe(window, "beforeunload", function()
   {
      if(timer)
      {
         timer.stop();   
      }
   })
};


