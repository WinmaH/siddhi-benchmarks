/*  
   NEXMark Generator -- Niagara Extension to XMark Data Generator

   Acknowledgements:
   The NEXMark Generator was developed using the xmlgen generator 
   from the XMark Benchmark project as a basis. The NEXMark
   generator generates streams of auction elements (bids, items
   for auctions, persons) as opposed to the auction files
   generated by xmlgen.  xmlgen was developed by Florian Waas.
   See http://www.xml-benchmark.org for information.

   Copyright (c) Dept. of  Computer Science & Engineering,
   OGI School of Science & Engineering, OHSU. All Rights Reserved.

   Permission to use, copy, modify, and distribute this software and
   its documentation is hereby granted, provided that both the
   copyright notice and this permission notice appear in all copies
   of the software, derivative works or modified versions, and any
   portions thereof, and that both notices appear in supporting
   documentation.

   THE AUTHORS AND THE DEPT. OF COMPUTER SCIENCE & ENGINEERING 
   AT OHSU ALLOW USE OF THIS SOFTWARE IN ITS "AS IS" CONDITION, 
   AND THEY DISCLAIM ANY LIABILITY OF ANY KIND FOR ANY DAMAGES 
   WHATSOEVER RESULTING FROM THE USE OF THIS SOFTWARE.

   This software was developed with support from NSF ITR award
   IIS0086002 and from DARPA through NAVY/SPAWAR 
   Contract No. N66001-99-1-8098.

*/

// encapsulates information about an open auction that must be saved

package org.wso2.siddhi.common.benchmarks.http;
import java.util.*;

class OpenAuction {
    public static int MAXAUCTIONLEN_SEC = 24*60*60; // 24 hours
    public static int MINAUCTIONLEN_SEC = 2*60*60; // 2 hours

    private int currPrice; // price in dollars
    private boolean closed = false;
    private long endTime;
    int numBids = 0; // for debugging purposes
    private Random rnd;
    
    public OpenAuction(SimpleCalendar cal, int virtualId, Random rnd) {
        currPrice = rnd.nextInt(200)+1; // initial price must be at least $1
        endTime = cal.getTimeInSecs() + rnd.nextInt(MAXAUCTIONLEN_SEC) + MINAUCTIONLEN_SEC;
        this.rnd = rnd;
    }
    
    // increase the price, return the new bid amount
    public int increasePrice() {
        int increase = rnd.nextInt(25)+1; // zero increases not allowed
        currPrice += increase;
        return currPrice;
    }
    
    // curr price is always an even dollar amount
    public int getCurrPrice() {
        return currPrice;
    }
    
    public long getEndTime() {
        return endTime;
    }

    public boolean isClosed(SimpleCalendar cal) {
        checkClosed(cal);
        return closed;
    }

    public void recordBid() {
        numBids++;
    }

    private void checkClosed(SimpleCalendar cal) {
        if(!closed && (cal.getTimeInSecs())
           > endTime) {
            //System.err.println("KT closing auction. Number of Bids: " + numBids);
            closed = true;
        }
        // KT - here is where we could create a closed_auction element
        // or do something to get a closed_auction created
        // but I'm not going to do it now
    }
}

