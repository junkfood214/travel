package com.example.Utlis;

import com.example.pojo.Way;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class generateWay {
    public static List<String> generate(List<Way> ways)
    {
        String start = findStart(ways);
        List<String>result = new ArrayList<>();
        if(!start.isEmpty())
        {
            result.add(start);
            for(int i=0;i<ways.size();i++)
            {
                String follow = findFlower(start,ways);
                if(!follow.isEmpty())
                {
                    result.add(follow);
                    start = follow;
                }
            }
            if(result.size() == ways.size()+1)
            {
                return result;
            }
            else
            {
                return null;
            }
        }
        return null;
    }

    public static String findStart(List<Way> ways)
    {
        for(int i = 0;i<ways.size();i++)
        {
            Way curWay = ways.get(i);
            boolean flag = false;
            for (Way way : ways) {
                if (Objects.equals(curWay.getStart(), way.getEnd())) {
                    flag = true;
                    break;
                }
            }
            if(!flag)
            {
                return curWay.getStart();
            }
        }
        return "";
    }

    public static String findFlower(String start,List<Way> ways)
    {
        if(!start.isEmpty())
        {
            for (Way way : ways) {
                if (start.equals(way.getStart())) {
                    return way.getEnd();
                }
            }
        }
        return "";
    }
}
