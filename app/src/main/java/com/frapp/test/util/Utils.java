package com.frapp.test.util;

import android.util.Log;

import com.frapp.test.data.DataModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Utils {

    private static Gson gson;

    public static List<DataModel> addTwoNonFeaturedListAndSort(List<DataModel> listOne, List<DataModel> listTwo) {
        listOne.addAll(listTwo);
        Collections.sort(listOne, new Comparator<DataModel>() {
            @Override
            public int compare(final DataModel object1, final DataModel object2) {
                return object2.getViews() - object1.getViews();
            }
        });

        return listOne;
    }

    public static List<DataModel> addTwoFeaturedListAndSort(List<DataModel> listOne, List<DataModel> listTwo) {
        listOne.addAll(listTwo);
        Collections.sort(listOne, new Comparator<DataModel>() {
            @Override
            public int compare(final DataModel object1, final DataModel object2) {
                if (object1.getFeatured() != null && object2.getFeatured() != null)
                    return object1.getFeatured() - object2.getFeatured();
                else return -1;
            }
        });

        return listOne;
    }

    public static List<DataModel> getInterleavedFinalLists(List<DataModel> featuredList, List<DataModel> nonFeaturedList, Map<String, List<DataModel>> toRemoveItemListMap) {

        int position = 0, previousPosition, result;


        //Remove Favourite items
        for (Map.Entry<String, List<DataModel>> entry : toRemoveItemListMap.entrySet()) {
            List<DataModel> toRemoveItem = entry.getValue();
            for(int i = 0 ; i < toRemoveItem.size() ; i++){

                result = getItemPositionInList(featuredList, toRemoveItem.get(i).getTitle(), toRemoveItem.get(i).getDescription());
                if(result != -1){
                    featuredList.remove(position);
                    continue;
                }

                result = getItemPositionInList(nonFeaturedList, toRemoveItem.get(i).getTitle(), toRemoveItem.get(i).getDescription());
                if(result != -1){
                    nonFeaturedList.remove(position);
                }
            }
        }

        //lists are setup as queues
        Deque<DataModel> featuredListQueue = new ArrayDeque<>(featuredList);
        Deque<DataModel> nonFeatureListQueue = new ArrayDeque<>(nonFeaturedList);

        List<DataModel> repeatedFeaturedList = new ArrayList<>();
        List<DataModel> resultList = new ArrayList<>(featuredListQueue.size() + nonFeatureListQueue.size());

        resultList.add(featuredListQueue.poll());
        position++;
        previousPosition = position;

        while (!featuredListQueue.isEmpty() && !nonFeatureListQueue.isEmpty()) {

            // if the position of featuredList is greater then or equal to current position of element to be added
            // then add the element from featuredList queue
            if (Objects.requireNonNull(featuredListQueue.peek()).getPosition() == position) {
                resultList.add(featuredListQueue.poll());
                previousPosition = position;
                position++;
            } else {
                // if item in featuredList is repeated add it to queue later
                if (previousPosition == Objects.requireNonNull(featuredListQueue.peek()).getPosition()) {
                    repeatedFeaturedList.add(featuredListQueue.poll());
                } else {
                    resultList.add(nonFeatureListQueue.poll());
                    position++;
                }
            }
        }
        // since the while loop terminates when one of the queues is empty
        // we should add the left-over elements
        resultList.addAll(repeatedFeaturedList);
        resultList.addAll(featuredListQueue);
        resultList.addAll(nonFeatureListQueue);

        for(DataModel dataModel : resultList)
            Log.e("FinalList",dataModel.getTitle() + "**" + dataModel.getPosition());
        return resultList;
    }

    private static int getItemPositionInList(List<DataModel> dataModelList, String title, String description){
        for(int i = 0 ; i < dataModelList.size() ; i++){
            if(dataModelList.get(i).getTitle().equals(title) && dataModelList.get(i).getDescription().equals(description))
                return i;
        }
        return -1;
    }

    public static Gson getGsonParser() {
        if (null == gson) {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
        }
        return gson;
    }


}
