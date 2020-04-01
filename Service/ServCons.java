package Service;

import Domain.*;
import Repository.Repo;
import Repository.RepoDoc;
import Repository.RepoDtoCons;
import javafx.util.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ServCons {
    //This class is the service for Consulting class
    private ServPatient servPatient;
    private ServDoc servDoc;
    private Repo<Integer, Consulting> repCons=new RepoDoc<>();
    private Repo<Integer, DtoConsulting> repDtoCons=new RepoDtoCons<>();


    private ServCons()
    {
    }

    public static ServCons getInstance() {
        return ServCons.ServCons_singleton.INSTANCE;
    }

    private static class ServCons_singleton {

        private static final ServCons INSTANCE = new ServCons();
    }

    public void setServ(ServDoc servDoc,ServPatient servPatient)
    {
        //This method sets the service of Patient class and the service of Doctor class
        //Input: servDoc is an object of ServDoc class, servPatient is an object of ServPatient
        //Output: -
        this.servDoc=servDoc;
        this.servPatient=servPatient;
    }

    private ArrayList<Patient> orderPat()
    {
        //This method creates a list of Patient objects ordered by age. In l variable is saved the list of Patient objects.
        // In  "list" variable is saved the ordered list.
        //Input: -
        //Output: list the list
        List l=new ArrayList<Patient>();
        servPatient.getRepPat().findAll().forEach(l::add);
        ArrayList<Patient> list=(ArrayList<Patient>)l.stream().sorted(Comparator.comparing(Patient::getAge)).collect(Collectors.toList());
        return list;
    }


    public void printOrder()
    {
        //This method prints to console the list of patient ordered by age
        //Input: -
        //Output: -
        ArrayList<Patient> list=orderPat();
        for(Patient p:list)
        {
            System.out.println(p);
        }
    }

    public void prindConsult()
    {
        //This method prints the list of medical consults saved in Consult Repository
        //Input:-
        //Output:-
        repCons.findAll().forEach(System.out::println);
    }

    private List<Doctor> createDocList()
    {
        //This method creates a list of doctors received from the Repository
        //Input: -
        //Output: listDoc - list
        List<Doctor> listDoc=new ArrayList<Doctor>();
        servDoc.getRepDoc().findAll().forEach(listDoc::add);
        return listDoc;
    }

    private Room[] createRoomList()
    {
        //This method creates an array of enum Room
        //Input:-
        //Output: rooms - array
        Room[] rooms=Room.values();
        return rooms;
    }

    private List<Integer> createWorkRoomList(Room[] rooms)
    {
        //This method creates a list. In this list is saved the time used by a doctor to consult in a room.
        //Input:rooms - array of Enum Room
        //Output: workRoomList- list
        List<Integer> workRoomList=new ArrayList<Integer>(Arrays.asList(new Integer[rooms.length]));
        Collections.fill(workRoomList,Consulting.getClinicOpen());
        return workRoomList;
    }

    private ArrayList<Integer> createRoomInd(List<Integer> workRoomList)
    {
        //This method creates a list. This list will help me to know in which rooms the doctors still work.
        //Input:workRoomList - list
        //Output:roomInd - list
        ArrayList<Integer> roomInd=new ArrayList<>();
        for(int i=0;i<workRoomList.size();i++)
            roomInd.add(i);
        return roomInd;
    }

    private ArrayList<Doctor> createRoomDocList(List<Integer> workRoomList,List<Doctor> listDoc)
    {
        //This method creates an array. In this list is saved the doctor that consults in every room. When I put a doctor into a room, I delete him from
        //Repository list
        //Input: workRoomList - list,listDoc - list of doctor given by Repository
        //Output: docInRoom - array of doctors
        ArrayList<Doctor> docInRoom=new ArrayList<>();
        for(int i=0;i<workRoomList.size();i++)
        {

            docInRoom.add(listDoc.get(0));
            servDoc.getRepDoc().delete(docInRoom.get(i).getId());
            listDoc.remove(0);
        }
        return docInRoom;
    }


    /*private void delRoom(Integer i,ArrayList<Integer> roomInd)
    {
        //This method deletes a consulting room
        //Input: i - integer,roomInd - list of rooms that are still open
        //Output:-
        roomInd.remove(i);
    }

    private void changeDoc(List<Doctor> listDoc,ArrayList<Integer> roomInd,ArrayList<Doctor> docInRoom,Integer i)
    {
        //This method puts a doctor in a consulting room
        //Input:listDoc - list of doctors received by Repository,roomInd - list of rooms that are still open, docInRoom - list of doctors for every room,
        //i-integer
        //Output:-
        docInRoom.set(roomInd.get(i),listDoc.get(0));
        servDoc.getRepDoc().delete(docInRoom.get(roomInd.get(i)).getId());
        listDoc.remove(0);
    }*/

    /*private void changeDocOrDelRoom(List<Doctor> listDoc,ArrayList<Integer> roomInd,ArrayList<Doctor> docInRoom,Integer i)
    {

        if(listDoc.size()==0)
        {
            delRoom(i,roomInd);
        }
        else
        {
            changeDoc(listDoc,roomInd,docInRoom,i);
        }
    }*/

    private void saveRepo(Room[] rooms,ArrayList<Integer> roomInd,ArrayList<Doctor> docInRoom,Patient patient,Integer i,Integer sumConsult,Integer timeConsult)
    {
        //This method saves in repository a new Consulting object.
        //Input: rooms - array of Room enum, roomInd - list of rooms that are still open,patient - a Patient object,
        // sumConsult - an integer that saves how much money doctor makes ,timeConsult - an integer that saves how much money doctor spends
        //Output:-
        Consulting cons=new Consulting(rooms[roomInd.get(i)],docInRoom.get(roomInd.get(i)).getFirst_name(),docInRoom.get(roomInd.get(i)).getLast_name(),docInRoom.get(roomInd.get(i)).getId(),patient.getId(),sumConsult,timeConsult);
        repCons.save(cons);
    }

//    private void delPatCons(ArrayList<Patient> listPat,Integer findPat)
//    {
//        servPatient.getRepPat().delete(listPat.get(findPat).getId());
//        listPat.remove(findPat);
//    }

    private void chooseDocAndPatRooms(ArrayList<Patient> listPat,List<Doctor> listDoc,Room[] rooms,List<Integer> workRoomList,ArrayList<Integer> roomInd,ArrayList<Doctor> docInRoom)
    {
        //This method selects a doctor for every room. In every room can be maximum one patient at a time. If a doctor has just a little bit of time to work
        //it will be chosen the patient that fits in the amount of time left. If the remaining time is too short or is 0, it will change
        //the doctor from the room only if is another doctor that can take his place, and the clinic is still open

        //Input:-listPat - array of patients,listDoc - list of doctors, rooms - aray of Room enum,
        // workRoomList - list of time that can spend in one room, roomInd - array of room that is still open,
        // docInRoom - array of doctors selected for each room

        //Output: -

        //will be executed while is room open
        while(roomInd.size()>0)
        {
            //will execute many operations for every remaining room
            for(int i=0;i<roomInd.size();i++)
            {

                // is checked if the doctor from a room has the time left to work bigger than the time clinic is still open
                //If it is, the working time of the doctor is set to the time the clinic closes
                if(docInRoom.get(roomInd.get(i)).getWorkTime()>workRoomList.get(roomInd.get(i)))
                {
                    docInRoom.get(roomInd.get(i)).setWorkTime(workRoomList.get(roomInd.get(i)));
                }
                boolean find=false;
                Integer timeConsult;
                Integer sumConsult;
                Patient patient=null;
                int findPat;
                // search for the right patient according to amount of time that a doctor could work
                for( findPat=0;findPat<listPat.size()&&find==false;findPat++)
                {
                    if(giveTimeConsult(listPat.get(findPat))<=docInRoom.get(roomInd.get(i)).getWorkTime())
                    {
                        patient=listPat.get(findPat);
                        find=true;
                    }
                }
                //check that is find a patient according to time that doctor should work
                if(find==false)
                {
                    //check if there is another doctor that can take the place of the current one. If it is not the room is closed and deleted from array of
                    // working room
                    if(listDoc.size()==0)
                    {
                        roomInd.remove(i);
                        i--;
                    }
                    else
                    {
                        docInRoom.set(roomInd.get(i),listDoc.get(0));
                        servDoc.getRepDoc().delete(docInRoom.get(roomInd.get(i)).getId());
                        listDoc.remove(0);
                        i--;
                    }
                    continue;
                }
                findPat--;

                //choose the amount of time that a patient will spend and the amount of money he will pay
                timeConsult=giveTimeConsult(patient);
                sumConsult=giveSumConsult(patient);
                // creates and saves a Consult object
                saveRepo(rooms,roomInd,docInRoom,patient,i,sumConsult,timeConsult);
                //deletes the patient that was consulted from the list of patients waiting to be consulted
                servPatient.getRepPat().delete(listPat.get(findPat).getId());
                listPat.remove(findPat);
                //reduces the working time a doctor has
                docInRoom.get(roomInd.get(i)).setWorkTime(docInRoom.get(roomInd.get(i)).getWorkTime()-timeConsult);
                //reduces the time  the clinic is opened
                workRoomList.set(roomInd.get(i),workRoomList.get(roomInd.get(i))-timeConsult);

                //checks if the clinic has enough time to consult another patient until it closes
                if(workRoomList.get(roomInd.get(i))<20)
                {
                    roomInd.remove(i);
                    i--;
                    continue;
                }

                //checks if the doctor has enough time to consult another patient until closing time
                if(docInRoom.get(roomInd.get(i)).getWorkTime()<20)
                {
                    if(listDoc.size()==0)
                    {
                        roomInd.remove(i);
                        i--;
                    }
                    else
                    {
                        docInRoom.set(roomInd.get(i),listDoc.get(0));
                        servDoc.getRepDoc().delete(docInRoom.get(roomInd.get(i)).getId());
                        listDoc.remove(0);
                    }
                }
            }
        }
    }

    public void addConsult()
    {
        //array with patients ordered by age
        ArrayList<Patient> listPat=orderPat();
        //list of doctors
        List<Doctor> listDoc=createDocList();
        //array of the rooms that can be used for consulting
        Room[] rooms=createRoomList();
        //array of the time left in the room for consulting patients
        List<Integer> workRoomList=createWorkRoomList(rooms);
        //array which saved what room is still in use
        ArrayList<Integer> roomInd=createRoomInd(workRoomList);
        //array of the doctor for every room
        ArrayList<Doctor> docInRoom=createRoomDocList(workRoomList,listDoc);

        chooseDocAndPatRooms(listPat,listDoc,rooms,workRoomList,roomInd,docInRoom);
    }


    private  List<Consulting> createConsList()
    {
        //This method creates a list with all the consultations
        //Input: -
        //Output: l - the list
        List<Consulting> l=new ArrayList<Consulting>();
        repCons.findAll().forEach(l::add);
        return l;
    }

    private ArrayList<Integer> createListIdDoc(List<Consulting> l)
    {
        //This method creates a list with the id of all the doctors that consult patients
        //Input: - l - list o Consults
        //Output: listId - list
        ArrayList<Integer> listId=new ArrayList<Integer>();
        l.stream().collect(Collectors.groupingBy( Consulting::getIdDoc,Collectors.counting())).keySet().forEach(listId::add);
        return listId;
    }

    private ArrayList<Long> createListCountConsult(List<Consulting> l)
    {
        //This method creates a list with the number of patients that was consulted by a doctor
        //Input: - l - list o Consults
        //Output: listCount1- list
        ArrayList<Long> listCount1=new ArrayList<Long>();
        l.stream().collect(Collectors.groupingBy( Consulting::getIdDoc,Collectors.counting())).values().forEach(listCount1::add);
        return  listCount1;
    }

    private ArrayList<Integer> createListSumConsult(List<Consulting> l)
    {
        //This method creates a list with the sum of money a doctor makes during consultations
        //Input: - l - list o Consults
        //Output: listSum1 - list
        ArrayList<Integer> listSum1=new ArrayList<Integer>();
        l.stream().collect(Collectors.groupingBy( Consulting::getIdDoc,Collectors.summingInt(Consulting::getReasonSuma))).values().forEach(listSum1::add);
        return  listSum1;
    }
    private ArrayList<Integer> createListTimeConsult(List<Consulting> l)
    {
        //This method creates a list of the time doctors spend consulting patients
        //Input: - l - list o Consults
        //Output: listTime - list
        ArrayList<Integer> listTime=new ArrayList<Integer>();
        l.stream().collect(Collectors.groupingBy( Consulting::getIdDoc,Collectors.summingInt(Consulting::getReasonTimp))).values().forEach(listTime::add);
        return  listTime;
    }

    public void creatDtoConsult()
    {
        //This method saves a copy of some attributes of consults
        //Input:-
        //Output:-

        List<Consulting> l=createConsList();
        ArrayList<Integer> listId=createListIdDoc(l);
        ArrayList<Long> listCount1=createListCountConsult(l);
        ArrayList<Integer> listSum1=createListSumConsult(l);
        ArrayList<Integer> listTime=createListTimeConsult(l);
        for(int i=0;i<listId.size();i++)
        {
            final int iterat=i;
            List<Consulting> doc=l.stream().filter(c->listId.get(iterat).equals(c.getIdDoc())).collect(Collectors.toList());
            DtoConsulting dtoConsulting=new DtoConsulting(listId.get(i),listCount1.get(i).intValue(),listSum1.get(i),listTime.get(i),doc.get(0).getFirstNameDoc(),doc.get(0).getLastNameDoc());
            repDtoCons.save(dtoConsulting);
        }

        //add the rest of doctors that does not consult anyone
        addRestOfDoc();

    }

    public void printDtoConsult()
    {
        //This method prints the copy of consults(DTO)
        //Input:-
        //Output:-
        repDtoCons.findAll().forEach(System.out::println);
    }

    private void addRestOfDoc()
    {
        //THis method adds the rest of doctors that are not consulting any patient
        //Input:-
        //Output:-
        List<Doctor> listDoc=new ArrayList<Doctor>();
        servDoc.getRepDoc().findAll().forEach(listDoc::add);
        for(int i=0;i<listDoc.size();i++)
        {
            DtoConsulting dtoConsulting=new DtoConsulting(listDoc.get(i).getId(),0,0,0,listDoc.get(i).getFirst_name(),listDoc.get(i).getLast_name());
            repDtoCons.save(dtoConsulting);
        }
    }

    private Integer giveSumConsult(Patient patient) {
        //This method selects the price that a patient should pay to be consulted
        //Input:patient - a Patient object
        //Output: sum of money
        switch (patient.getReason())
        {
            case Consultation:
            {
                return 50;
            }
            case Treatment:
            {
                return 35;
            }
            case  Prescriptions:
            {
                return 20;
            }

        }
        return null;
    }

    private Integer giveTimeConsult(Patient patient) {
        //This method selects the time that a patient spend to be consulted
        //Input:patient - a Patient object
        //Output: time
        switch (patient.getReason())
        {
            case Consultation:
            {
                return 30;
            }
            case Treatment:
            {
                return 40;
            }
            case  Prescriptions:
            {
                return 20;
            }
        }
        return null;
    }


}
